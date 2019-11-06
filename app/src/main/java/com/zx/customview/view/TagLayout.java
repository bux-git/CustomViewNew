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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            Rect rect = childRect.get(i);
            childView.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }
}
