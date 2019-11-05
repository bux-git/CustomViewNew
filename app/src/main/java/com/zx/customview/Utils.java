package com.zx.customview;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * description：
 * author：bux on 2019/11/4 11:25
 * email: 471025316@qq.com
 */
public class Utils {

    public static int dp2px(float dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
