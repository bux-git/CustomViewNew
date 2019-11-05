package com.zx.customview.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

import com.zx.customview.R;
import com.zx.customview.Utils;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * description：
 * author：bux on 2019/11/4 10:42
 * email: 471025316@qq.com
 */
public class MaterialEditText extends AppCompatEditText {

    private static final String TAG = "MaterialEditText";

    private int leftLabelPadding = Utils.dp2px(30);
    private int topLabelPadding = Utils.dp2px(30);
    ;
    private int labelBottomMargin = Utils.dp2px(30);
    ;
    private int labelTextSize = Utils.dp2px(10);
    private boolean labelOpen = false;

    //label高度
    private int labelTextHeight;

    private float labelFraction = 0f;

    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MaterialEditText(Context context) {
        this(context, null);

    }

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1);
    }

    public MaterialEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    public float getLabelFraction() {
        return labelFraction;
    }

    public void setLabelFraction(float labelFraction) {
        this.labelFraction = labelFraction;
        mPaint.setAlpha((int) (this.labelFraction * 255));
        invalidate();
    }

    public int getLabelTextSize() {
        return labelTextSize;
    }

    public void setLabelTextSize(int labelTextSize) {
        this.labelTextSize = labelTextSize;
        setLabelTextSize();
    }

    public boolean isLabelOpen() {
        return labelOpen;
    }

    public void setLabelOpen(boolean labelOpen) {
        this.labelOpen = labelOpen;
        setLabelShow();
    }

    void init(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText, defStyleAttr, 0);
        labelOpen = typedArray.getBoolean(R.styleable.MaterialEditText_labelOpen, false);
        labelTextSize = typedArray.getDimensionPixelSize(R.styleable.MaterialEditText_labelSize, 10);
        typedArray.recycle();

        mPaint.setAlpha((int) (labelFraction * 255));
        setLabelTextSize();

        setLabelShow();
    }

    private void setLabelShow() {
        if (labelOpen) {
            setPadding(getPaddingLeft(), getPaddingTop() + topLabelPadding + labelBottomMargin + labelTextHeight, getPaddingRight(), getPaddingBottom());
            addTextChangedListener(new TextWatcher() {
                boolean labelIsShowdown = false;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    //已经显示且为空时消失
                    if (labelIsShowdown && TextUtils.isEmpty(s)) {
                        labelIsShowdown = !labelIsShowdown;
                        getLabelAnimator().reverse();

                    } else if (!labelIsShowdown && !TextUtils.isEmpty(s)) {
                        labelIsShowdown = !labelIsShowdown;
                        //未显示且不为空时显示
                        getLabelAnimator().start();
                    }
                }
            });

        } else {
            Rect rect = new Rect();
            getBackground().getPadding(rect);

            setPadding(rect.left,rect.top,rect.right,rect.bottom);
            Log.d(TAG, "setLabelShow: "+rect.toString());
        }
    }

    private ObjectAnimator getLabelAnimator() {
        return ObjectAnimator.ofFloat(MaterialEditText.this, "labelFraction", 0, 1)
                .setDuration(300);
    }

    /**
     * 计算出label文字占用的高度
     */
    private void setLabelTextSize() {
        mPaint.setTextSize(labelTextSize);
        String hint = getHint().toString();
        if (TextUtils.isEmpty(hint)) {
            labelTextHeight = (int) mPaint.getFontSpacing();
        } else {
            Rect rect = new Rect();
            mPaint.getTextBounds(hint, 0, hint.length(), rect);
            labelTextHeight = rect.bottom - rect.top;
            Log.d(TAG, "setLabelTextSize: " + rect.toString());
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (labelOpen) {
            canvas.drawText(getHint(), 0
                    , getHint().length()
                    , getPaddingLeft() + leftLabelPadding * labelFraction, getPaddingTop() - labelBottomMargin * labelFraction
                    , mPaint);
        }

    }
}
