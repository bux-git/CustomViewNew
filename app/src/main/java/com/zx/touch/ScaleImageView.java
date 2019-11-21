package com.zx.touch;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import com.zx.Utils;
import com.zx.customview.R;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.viewpager.widget.ViewPager;

/**
 * @description：1.中心显示图片 2.默认最大边贴边
 * 3.双击最小边贴边放大 或缩小 使用动画
 * 4.图片可以拖动
 * 5.滑动冲突
 *
 * 6.快速fling处理
 * 7.多个手指缩放
 * @author：bux on 2019/11/20 10:39
 * @email: 471025316@qq.com
 */
public class ScaleImageView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private static final String TAG = "ScaleImageView";
    private static final int BITMAP_WIDTH = Utils.dp2px(300);
    private static final float OVER_SCALE_FRACT = 1.3F;
    private Bitmap mBitmap;
    private Paint mPaint;

    float offsetX;
    float offsetY;
    private float smallScale;
    private float bigScale;
    private boolean big = false;
    private final GestureDetectorCompat detectorCompat;
    //动画缩放比例
    private float scaleFraction;

    //手指移动距离
    private float transX;
    private float transY;
    private float xMax;
    private float yMax;


    ViewParent dstParent;


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

        detectorCompat = new GestureDetectorCompat(getContext(), this);
        detectorCompat.setOnDoubleTapListener(this);


    }

    public float getScaleFraction() {
        return scaleFraction;
    }

    public void setScaleFraction(float scaleFraction) {
        this.scaleFraction = scaleFraction;
        invalidate();
    }




    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        //根据宽高比 设置大小模式 缩放比率
        float widthScale = getWidth() / (float) mBitmap.getWidth();
        float heightScale = getHeight() / (float) mBitmap.getHeight();
        smallScale = Math.min(widthScale, heightScale);
        bigScale = Math.max(widthScale, heightScale) * OVER_SCALE_FRACT;

        //计算滑动边界 为图片放大倍数后减去 View对应宽高/2
        xMax = (mBitmap.getWidth() * bigScale - getWidth()) / 2;
        yMax = (mBitmap.getHeight() * bigScale - getHeight()) / 2;


        offsetX = getWidth() / 2f - mBitmap.getWidth() / 2f;
        offsetY = getHeight() / 2f - mBitmap.getHeight() / 2f;


        Log.d(TAG, "onSizeChanged: " + smallScale + "-" + bigScale);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(transX, transY);
        float scale = smallScale + (bigScale - smallScale) * scaleFraction;
        canvas.scale(scale, scale, getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(mBitmap, offsetX, offsetY, mPaint);

    }

    ObjectAnimator scaleAnimator;

    private ObjectAnimator getScaleAnimator() {
        if (scaleAnimator == null) {
            PropertyValuesHolder scaleHolder = PropertyValuesHolder.ofFloat("scaleFraction", 0, 1);

            scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(this, scaleHolder)
                    .setDuration(500);

            scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (!big) {
                        transX -= transX * (1 - (float) animation.getAnimatedValue());
                        transY -= transY * (1 - (float) animation.getAnimatedValue());
                        Log.d(TAG, "onAnimationUpdate: " + transX + "-" + transY);
                    }


                }
            });
        }


        return scaleAnimator;
    }

    private void disallowIntercept() {
        if (dstParent != null) {
            return;
        }
        ViewParent parent = null;
        while (true) {
            if (parent != null) {
                parent = parent.getParent();
            } else {
                parent = getParent();
            }

            Log.d(TAG, "while: ");
            if (parent instanceof ViewPager) {
                dstParent = parent;
                break;
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detectorCompat.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        disallowIntercept();
        if (!big) {
            dstParent.requestDisallowInterceptTouchEvent(false);
        } else {
            dstParent.requestDisallowInterceptTouchEvent(true);
           /* if (transX >= xMax) {
                dstParent.requestDisallowInterceptTouchEvent(false);
            } else {
                dstParent.requestDisallowInterceptTouchEvent(true);
            }

            if (transX <= -xMax) {
                dstParent.requestDisallowInterceptTouchEvent(false);

            } else {
                dstParent.requestDisallowInterceptTouchEvent(true);
            }*/

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
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG, "onScroll: " + distanceX + "-" + distanceY);

        if (big) {
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
            invalidate();
        }


        return false;
    }


    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: ");
        big = !big;
        if (big) {
            getScaleAnimator().start();
        } else {
            getScaleAnimator().reverse();
        }

        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
