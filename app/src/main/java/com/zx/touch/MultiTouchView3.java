package com.zx.touch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import com.zx.Utils;

import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 * @description：
 * @author：bux on 2019/11/22 22:35
 * @email: 471025316@qq.com
 */
public class MultiTouchView3 extends View {

    Paint mPaint = new Paint();
    ArrayList<Path> caches;
    SparseArray<Path> mPaths;
    private Rect mRect;

    ViewParent mDstParent;
    private float slop=Utils.dp2px(8);

    public MultiTouchView3(Context context) {
        super(context);
    }

    public MultiTouchView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiTouchView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MultiTouchView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    {
        mRect = new Rect(50, 50, 300, 300);
        caches = new ArrayList<>();
        mPaths = new SparseArray<>();

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(Utils.dp2px(2));
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(Utils.dp2px(16));

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mDstParent = Utils.getDisallowInterceptParent(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mRect, mPaint);
        for (int i = 0; i < mPaths.size(); i++) {
            canvas.drawPath(mPaths.valueAt(i), mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(pointInRect(event.getX(),event.getY())){
            mDstParent.requestDisallowInterceptTouchEvent(false);
           return false;
        }else{
            mDstParent.requestDisallowInterceptTouchEvent(true);
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Path path = null;
                if (caches.size() == 0) {
                    path = new Path();
                } else {
                    path = caches.get(0);
                    path.reset();
                    caches.remove(0);
                }

                path.moveTo(event.getX(event.getActionIndex()), event.getY(event.getActionIndex()));
                mPaths.put(event.getPointerId(event.getActionIndex()), path);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    int key = event.getPointerId(i);
                    Path movePath = mPaths.get(key);
                    movePath.lineTo(event.getX(i), event.getY(i));
                }
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                int key = event.getPointerId(event.getActionIndex());
                Path cPath = mPaths.get(key);
                mPaths.remove(key);
                caches.add(cPath);
                invalidate();
                break;
            default:
        }

        return true;
    }


    public boolean pointInRect(float localX, float localY) {
        return localX > (mRect.left-slop) && localX < (mRect.right+ slop)
                && localY > (mRect.top - slop) && localY < (mRect.bottom + slop);
    }
}
