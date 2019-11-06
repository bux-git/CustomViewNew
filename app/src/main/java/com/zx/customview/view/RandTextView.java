package com.zx.customview.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import com.zx.customview.Utils;

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
            , Color.YELLOW
            , Color.MAGENTA
            , Color.GRAY};

    public static String TEXTS[] = {"RED"
            , "BLUE"
            , "GREEN"
            , "BLACK"
            , "CYAN"
            , "YELLOW"
            , "MAGENTA"
            , "GRAY"};

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
        int random=new Random().nextInt(BG_COLORS.length);
        int color = BG_COLORS[random];
        setTextColor(Color.WHITE);
        setPadding(RADIUS, RADIUS, RADIUS, RADIUS);
        setText(TEXTS[random]);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(RADIUS);
        drawable.setColor(color);
        setBackground(drawable);

    }

}
