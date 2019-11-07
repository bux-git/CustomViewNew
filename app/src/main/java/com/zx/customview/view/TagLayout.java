package com.zx.customview.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
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
    private static final String TAG = "TagLayout";

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
        int totalHeight = getPaddingBottom() + getPaddingTop();
        int totalWidth = 0;

        int lineHeight = 0;
        int lineWidth = 0;


        //测量子View
        for (int i = 0; i < getChildCount(); i++) {

            View child = getChildAt(i);
            //测量子View尺寸
            measureChildWithMargins(child, widthMeasureSpec, getPaddingLeft() + getPaddingRight(), heightMeasureSpec, totalHeight);
            //计算最大宽度 及 累积高度
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            int childHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            //换行条件 UNSPECIFIED >0时 使用父View提供的尺寸，==0时不限制尺寸
            if (widthSize > 0 && childWidth > widthSize-lineWidth) {

                //换行时 计算 所有行总高度  及最大宽度
                totalHeight += lineHeight;
                totalWidth = Math.max(totalWidth, lineWidth);

                //初始化新行记录数据
                lineWidth = 0;
                lineHeight = 0;

            }

            //记录子View位置
            childRect.get(i).left =lineWidth  + getPaddingLeft();
            childRect.get(i).top = totalHeight + getPaddingTop();
            childRect.get(i).right = lineWidth + getPaddingLeft() + child.getMeasuredWidth();
            childRect.get(i).bottom = totalHeight + getPaddingTop() + child.getMeasuredHeight();


            //为换行时，计算当前行 累积宽度 和最大高度
            lineHeight = Math.max(lineHeight, childHeight);
            lineWidth += childWidth;




        }
       // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算宽高
        Log.d(TAG, "onMeasure: "+totalWidth+"   "+totalHeight);
        setMeasuredDimension(resolveSize(totalWidth,widthMeasureSpec), resolveSize(totalHeight,heightMeasureSpec));

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
