package com.zx.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;

/**
 * @description：
 * @author：bux on 2019/11/25 11:14
 * @email: 471025316@qq.com
 */
public class NestScrollingParent2Layout extends LinearLayout implements NestedScrollingParent2 {
    private static final String TAG = "NestScrollingParent2Lay";
    private View mTopView;
    private View mContentView;
    private View mBottomView;
    private int mTopViewHeight;


    private NestedScrollingParentHelper mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);

    public NestScrollingParent2Layout(Context context) {
        this(context, null);
    }

    public NestScrollingParent2Layout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestScrollingParent2Layout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            mTopView = getChildAt(0);
        }
        if (getChildCount() > 1) {
            mContentView = getChildAt(1);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //ViewPager修改后的高度= 总高度-导航栏高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams layoutParams = mContentView.getLayoutParams();
        layoutParams.height = getMeasuredHeight();
        mContentView.setLayoutParams(layoutParams);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mTopView != null) {
            mTopViewHeight = mTopView.getMeasuredHeight();
        }
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
   /*     Log.d(TAG, "onStartNestedScroll: " + axes
                + " " + ViewCompat.SCROLL_AXIS_VERTICAL
                + "  " + (axes & ViewCompat.SCROLL_AXIS_VERTICAL));*/
        Log.d(TAG, "onStartNestedScroll: ");
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        Log.d(TAG, "onNestedScrollAccepted: ");
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        Log.d(TAG, "onStopNestedScroll: ");
        mNestedScrollingParentHelper.onStopNestedScroll(target, type);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.d(TAG, "onNestedScroll: "+dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

        if (dy < 0) {
            if (getScrollY() <= 0) {
                return;
            } else {
                int leftOverDy = getScrollY();
                consumed[1]=leftOverDy > Math.abs(dy) ? dy : -leftOverDy;

            }
        } else if (dy > 0) {
            //向上剩余滑动距离
            int leftOverDy = mTopViewHeight - getScrollY();
            if (leftOverDy <= 0) {
                return;
            } else {
                consumed[1] = leftOverDy >= dy ? dy : leftOverDy;
            }

        }
        Log.d(TAG, "onNestedPreScroll: " + dy+" consumed[1]:"+consumed[1]+" getScrollY:"+getScrollY());
        scrollBy(0, consumed[1] );
    }

    @Override
    public void scrollTo(int x, int y) {
     /*   if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }*/

        super.scrollTo(x, y);
    }
}
