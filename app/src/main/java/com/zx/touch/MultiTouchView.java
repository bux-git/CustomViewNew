package com.zx.touch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import com.zx.Utils;
import com.zx.customview.R;

import androidx.annotation.Nullable;

/**
 * @description：
 * @author：bux on 2019/11/22 14:46
 * @email: 471025316@qq.com
 */
public class MultiTouchView extends View {
    private static final String TAG = "MultiTouchView";
    private static final float IMAGE_WIDTH = Utils.dp2px(300);
    private static final float slop = Utils.dp2px(5);
    private Bitmap mBitmap;
    private Paint mPaint;

    private ViewParent dstParent;


    private float offsetX;
    private float offsetY;
    private float lastX;
    private float lastY;
    private int trackingId;

    public MultiTouchView(Context context) {
        super(context);
    }

    public MultiTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiTouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MultiTouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    {
        mPaint = new Paint();
        mBitmap = Utils.calculateInSampleSize(getContext(), R.drawable.test2, (int) IMAGE_WIDTH, (int) IMAGE_WIDTH);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, offsetX, offsetY, mPaint);
    }

    /*
    1.第一个手指按下触发ACTION_DOWN
        ACTION_MOVE
        ACTION_MOVE
        ACTION_POINTER_DOWN 多个手指时，其他手指按下
        ACTION_MOVE
        ACTION_MOVE
        ACTION_MOVE
        ACTION_POINTER_UP 多个手指时，有手指抬起

        ACTION_UP 最后一个手指抬起


        需求:当新手指按下时 使用新手指滑动
        有手指，抬起时默认选择最后一个手指
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        setDisallowInterceptParent(pointInView(event.getX(), event.getY()));

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: ACTION_DOWN");
                lastX = event.getX(0);
                lastY = event.getY(0);
                break;
            case MotionEvent.ACTION_MOVE:
                int mIndex = Math.max(event.findPointerIndex(trackingId), 0);
                Log.d(TAG, "onTouchEvent: " + mIndex);
                offsetX += event.getX(mIndex) - lastX;
                offsetY += event.getY(mIndex) - lastY;

                lastX = event.getX(mIndex);
                lastY = event.getY(mIndex);
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(TAG, "onTouchEvent: ACTION_POINTER_DOWN");
                //此方法只在pointer中有效
                //记录新手指Id
                int index = event.getActionIndex();
                trackingId = event.getPointerId(index);
                //记录位置
                lastX = event.getX(index);
                lastY = event.getY(index);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(TAG, "onTouchEvent: ACTION_POINTER_UP");
                int upIndex = event.getActionIndex();
                int upId = event.getPointerId(upIndex);

                upIndex = upId == trackingId ? event.getPointerCount() - 2 : event.getPointerCount() - 1;
                trackingId = event.getPointerId(upIndex);

                lastX = event.getX(upIndex);
                lastY = event.getY(upIndex);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: ACTION_UP");
                break;
            default:
        }
        return true;
    }


    private void setDisallowInterceptParent(boolean disallowInterceptParent) {
        if (dstParent != null) {
            dstParent.requestDisallowInterceptTouchEvent(disallowInterceptParent);
            return;
        }
        dstParent = Utils.getDisallowInterceptParent(this);

    }

    public boolean pointInView(float localX, float localY) {
        return localX > (offsetX - slop) && localX < (offsetX + mBitmap.getWidth() + slop)
                && localY > (offsetY - slop) && localY < (offsetY + mBitmap.getWidth() + slop);
    }

}
