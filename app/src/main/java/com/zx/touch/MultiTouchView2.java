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
public class MultiTouchView2 extends View {
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

    public MultiTouchView2(Context context) {
        super(context);
    }

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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


        需求:多个手指合作，取多点中间位置为焦点
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int pointerCount = event.getPointerCount();

        float sumX = 0;
        float sumY = 0;

        boolean isPointUp = event.getActionMasked() == MotionEvent.ACTION_POINTER_UP;
        for (int i = 0; i < pointerCount; i++) {
            if (isPointUp && event.getActionIndex() == i) {
                continue;
            }
            sumX += event.getX(i);
            sumY += event.getY(i);
        }
        //当前协作焦点坐标
        if (isPointUp) {
            pointerCount--;
        }

        float forceX = sumX / pointerCount;
        float forceY = sumY / pointerCount;

        setDisallowInterceptParent(pointInView(forceX, forceY));

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                lastX = forceX;
                lastY = forceY;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent:ACTION_MOVE " + forceX + "-" + forceY + " count:" + pointerCount);
                offsetX += forceX - lastX;
                offsetY += forceY - lastY;

                lastX = forceX;
                lastY = forceY;
                invalidate();
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
