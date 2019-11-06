package com.zx.customview.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * description：一.测量宽度
 * 1.测量子View尺寸
 * 2.当子测量自己尺寸
 * 3.当剩余宽度小于子View时，换行
 * 4.计算子View位置
 * 二.循环为子View布局
 * author：bux on 2019/11/6 17:35
 * email: 471025316@qq.com
 */
public class TagLayout extends ViewGroup {


    private List<Rect> childRect = new ArrayList<>();

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        childRect.clear();
        for (int i = 0; i < getChildCount(); i++) {

            childRect.add(new Rect());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);


        //子view总共高度
        int totalHeight = 0;

        int widthUsed = 0;
        int maxHeight = 0;
        int maxWidth = 0;

        //测量子View
        for (int i = 0; i < getChildCount(); i++) {

            View child = getChildAt(i);
            //测量子View尺寸
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, totalHeight);
            //计算最大宽度 及 累积高度
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            int childHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            widthUsed += childWidth;
            maxHeight = Math.max(maxHeight, childHeight);

            //计算换行条件
            if (widthMode != MeasureSpec.UNSPECIFIED
                    && widthSize + getPaddingLeft() + getPaddingRight() - widthUsed < childWidth) {
                widthUsed = 0;
                totalHeight += maxHeight;
            } else {

            }


            //记录子View位置
            childRect.get(i).left = widthUsed + getPaddingLeft();
            childRect.get(i).top = heightUsed + getPaddingTop();
            childRect.get(i).right = widthUsed + getPaddingLeft() + child.getMeasuredWidth();
            childRect.get(i).bottom = heightUsed + getPaddingTop() + child.getMeasuredHeight();

        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            Rect rect = childRect.get(i);
            childView.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
