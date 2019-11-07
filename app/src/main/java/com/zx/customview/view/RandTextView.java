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
            , Color.LTGRAY
            , Color.MAGENTA
            , Color.GRAY};

    public static String TEXTS[] = {"北京市","天津市","上海市","重庆市","河北省","山西省","辽宁省","吉林省","黑龙江省","江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省","广东省","海南省","四川省","贵州省","云南省","陕西省","甘肃省","青海省","台湾省","内蒙古自治区","广西壮族自治区","西藏自治区","宁夏回族自治区","新疆维吾尔自治区","香港特别行政区","澳门特别行政区"};

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
        setText(TEXTS[new Random().nextInt(TEXTS.length)]);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(RADIUS);
        drawable.setColor(color);
        setBackground(drawable);

    }

}
