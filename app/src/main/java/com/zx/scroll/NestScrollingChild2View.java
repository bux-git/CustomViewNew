package com.zx.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

/**
 * @description：
 * @author：bux on 2019/11/26 12:13
 * @email: 471025316@qq.com
 */
public class NestScrollingChild2View extends LinearLayout implements NestedScrollingChild2 {
    private static final String TAG = "NestScrollingChild2View";

    NestedScrollingChildHelper mChildHelper = new NestedScrollingChildHelper(this);
    private float lastX;
    private float lastY;
    private int[] consumed = new int[2];


    public NestScrollingChild2View(Context context) {
        super(context);
    }

    public NestScrollingChild2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestScrollingChild2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NestScrollingChild2View(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    {
        setNestedScrollingEnabled(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: "+event.getActionMasked()
                +"  getX():"+event.getX()+"  getY:"+event.getY()
                +"  rawX():"+event.getRawX()+"  rawY:"+event.getRawY());
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_TOUCH);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = lastX - event.getX();
                float dy = lastY - event.getY();
                dispatchNestedPreScroll((int)dx, (int)dy, consumed, null, ViewCompat.TYPE_TOUCH);
                break;
            case MotionEvent.ACTION_UP:
                stopNestedScroll(ViewCompat.TYPE_TOUCH);
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return mChildHelper.startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll(int type) {
        mChildHelper.stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return mChildHelper.hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mChildHelper.setNestedScrollingEnabled(enabled);
    }
}
