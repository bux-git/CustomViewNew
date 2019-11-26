package com.zx.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import com.zx.Utils;

import java.util.Random;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * description：
 * author：bux on 2019/11/6 16:49
 * email: 471025316@qq.com
 */
public class RandTextView extends AppCompatTextView {

    public static final int RADIUS = Utils.dp2px(8);

    public static int BG_COLORS[] = {Color.RED
            , Color.BLUE
            , Color.GREEN
            , Color.BLACK
            , Color.CYAN
            , Color.LTGRAY
            , Color.MAGENTA
            , Color.GRAY};



    public RandTextView(Context context) {
        super(context);
    }

    public RandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    {

        int color = BG_COLORS[new Random().nextInt(BG_COLORS.length)];
        setTextColor(Color.WHITE);
        setPadding(RADIUS, RADIUS, RADIUS, RADIUS);
        setText(Utils.TEXTS[new Random().nextInt(Utils.TEXTS.length)]);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(RADIUS);
        drawable.setColor(color);
        setBackground(drawable);

    }

}
