package com.zx.touch;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewParent;
import android.widget.OverScroller;

import com.zx.Utils;
import com.zx.customview.R;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

/**
 * @description：1.中心显示图片 2.默认最大边贴边
 * 3.双击最小边贴边放大 或缩小 使用动画
 * 4.图片可以拖动
 * 5.滑动冲突
 * <p>
 * 6.快速fling处理
 * 7.多个手指缩放
 * @author：bux on 2019/11/20 10:39
 * @email: 471025316@qq.com
 */
public class ScaleImageView extends View {
    private static final String TAG = "ScaleImageView";
    private static final int BITMAP_WIDTH = Utils.dp2px(300);
    private static final float OVER_SCALE_FRACT = 1.3F;
    //惯性滑动距离
    private static final int FLING_OVER_Y = Utils.dp2px(20);
    private static final int FLING_OVER_X = Utils.dp2px(20);

    private Bitmap mBitmap;
    private Paint mPaint;

    float offsetX;
    float offsetY;
    private float smallScale;
    private float bigScale;
    private boolean big = false;
    private final GestureDetectorCompat detectorCompat;
    ScaleGestureDetector mScaleGestureDetector;

    //动画缩放比例
    private float curScale;

    //手指移动距离
    private float transX;
    private float transY;
    private float xMax;
    private float yMax;


    ViewParent dstParent;
    private OverScroller overScroller;
    private OverRunable overRunnable;


    public ScaleImageView(Context context) {
        super(context);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    {
        mBitmap = Utils.calculateInSampleSize(getContext(), R.drawable.test, BITMAP_WIDTH, BITMAP_WIDTH);
        mPaint = new Paint();
        GestureListener gestureListener = new GestureListener();
        detectorCompat = new GestureDetectorCompat(getContext(), gestureListener);
        detectorCompat.setOnDoubleTapListener(gestureListener);
        overRunnable = new OverRunable();
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureListener());

    }

    public float getCurScale() {
        return curScale;
    }

    public void setCurScale(float curScale) {
        this.curScale = curScale;
        invalidate();
    }

    private float maxBitmapWidth() {
        return mBitmap.getWidth() * bigScale;
    }

    private float maxBitmapHeight() {
        return mBitmap.getHeight() * bigScale;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        //根据宽高比 设置大小模式 缩放比率
        float widthScale = getWidth() / (float) mBitmap.getWidth();
        float heightScale = getHeight() / (float) mBitmap.getHeight();
        smallScale = Math.min(widthScale, heightScale);
        bigScale = Math.max(widthScale, heightScale) * OVER_SCALE_FRACT;
        curScale = smallScale;

        //计算滑动边界 为图片放大倍数后减去 View对应宽高/2
        xMax = (maxBitmapWidth() - getWidth()) / 2;
        yMax = (maxBitmapHeight() - getHeight()) / 2;

        offsetY = (getHeight() - mBitmap.getHeight()) / 2f;
        offsetX = (getWidth() - mBitmap.getWidth()) / 2f;

        Log.d(TAG, "onSizeChanged: " + smallScale + "-" + bigScale);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float scaleFraction = (curScale - smallScale) / (bigScale - smallScale);
        canvas.translate(transX * scaleFraction, transY * scaleFraction);
        canvas.scale(curScale, curScale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(mBitmap
                , offsetX
                , offsetY
                , mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = mScaleGestureDetector.onTouchEvent(event);
        if (!mScaleGestureDetector.isInProgress()) {
            return detectorCompat.onTouchEvent(event);
        }
        return result;
    }

    ObjectAnimator scaleAnimator;

    private ObjectAnimator getScaleAnimator() {
        if (scaleAnimator == null) {
            PropertyValuesHolder scaleHolder = PropertyValuesHolder.ofFloat("curScale", smallScale, bigScale);

            scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(this, scaleHolder);
        }
        scaleAnimator.setFloatValues(smallScale, bigScale);


        return scaleAnimator;
    }

    private void disallowIntercept() {
        if (dstParent != null) {
            return;
        }
        dstParent = Utils.getDisallowInterceptParent(this);
    }


    private class OverRunable implements Runnable {
        @Override
        public void run() {
            if (overScroller.computeScrollOffset()) {
                transX = overScroller.getCurrX();
                transY = overScroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }


    private class GestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onDown(MotionEvent e) {
            disallowIntercept();
            if (!big) {
                dstParent.requestDisallowInterceptTouchEvent(false);
            } else {
                dstParent.requestDisallowInterceptTouchEvent(true);

            }
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(TAG, "onDoubleTap: ");
            big = !big;
            if (big) {
                //点击处放大 平移放大后偏移量
                transX = (getWidth() / 2f - e.getX()) * (bigScale / smallScale - 1);
                transY = (getHeight() / 2f - e.getY()) * (bigScale / smallScale - 1);
                checkTransValue();
                getScaleAnimator().start();
            } else {
                getScaleAnimator().reverse();
            }

            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "onScroll \ne1: " + e1.getX() + "-" + e1.getY()
                    + "\r\ne2:" + e2.getX() + "-" + e2.getY()
                    + "\r\n distance:" + distanceX + "-" + distanceY);

            if (!big) {
                return false;
            }
            transX -= distanceX;
            transY -= distanceY;

            //右滑动
            if (distanceX <= 0) {
                if (transX >= xMax) {
                    dstParent.requestDisallowInterceptTouchEvent(false);
                } else {
                    dstParent.requestDisallowInterceptTouchEvent(true);
                }
            } else {
                if (transX <= -xMax) {
                    dstParent.requestDisallowInterceptTouchEvent(false);

                } else {
                    dstParent.requestDisallowInterceptTouchEvent(true);
                }
            }
            checkTransValue();

            invalidate();
            return false;
        }

        private void checkTransValue() {
            //判断滑动边界
            if (transX < -xMax) {
                transX = -xMax;
            }
            if (transX > xMax) {
                transX = xMax;
            }

            if (transY < -yMax) {
                transY = -yMax;
            }

            if (transY > yMax) {
                transY = yMax;
            }
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (!big) {
                return false;
            }

            if (overScroller == null) {
                overScroller = new OverScroller(getContext());
            }

            overScroller.fling((int) transX, (int) transY
                    , (int) velocityX, (int) velocityY
                    , (int) -xMax, (int) xMax
                    , (int) -yMax, (int) yMax
                    , FLING_OVER_X, FLING_OVER_Y);
            postOnAnimation(overRunnable);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }


        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }


        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }

    }

    private class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
        float initScale;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            curScale = initScale * detector.getScaleFactor();

            if (curScale > bigScale) {
                curScale = bigScale;
            }
            if (curScale < smallScale) {
                curScale = smallScale;
            }

            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            initScale = curScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}
