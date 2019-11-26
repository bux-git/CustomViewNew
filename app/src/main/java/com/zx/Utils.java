package com.zx;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;

import androidx.viewpager.widget.ViewPager;

/**
 * description：
 * author：bux on 2019/11/4 11:25
 * email: 471025316@qq.com
 */
public class Utils {

    public static int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }


    public Bitmap getBitMap(int width) {
        Bitmap bitmap = null;


        return bitmap;

    }

    /**
     * 解析图片 并缩放
     *
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap calculateInSampleSize(Context context, int resId, int reqWidth, int reqHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);

        int srcHeight = options.outHeight;
        int srcWidth = options.outWidth;

        options.inSampleSize = 1;
        //图片宽或高 大于目标宽高 需要缩放
        if (srcHeight > reqHeight || srcWidth > reqWidth) {
            float dstHRadio = (float) srcHeight / reqHeight;
            float dstWRadio = (float) srcWidth / reqWidth;
            float size = Math.max(dstHRadio, dstWRadio);
            //向上舍入
            options.inSampleSize = (int) Math.ceil(size);

            Log.d("calculateInSampleSize: ", "src:" + srcWidth + "-" + srcHeight
                    + "\r\nreq:" + reqWidth + "-" + reqHeight
                    + "\r\nRadio:" + dstHRadio + "-" + dstWRadio);
        }

        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
        Log.d("calculateInSampleSize", "w:h " + bitmap.getWidth() + ":" + bitmap.getHeight() + "  inSampleSize:" + options.inSampleSize);
        return bitmap;
    }

    public static ViewParent getDisallowInterceptParent(View view) {

        ViewParent parent = null;
        for (int i = 0; i < 4; i++) {
            if (parent != null) {
                parent = parent.getParent();
            } else {
                parent = view.getParent();
            }

            if (parent instanceof ViewPager) {
                return parent;
            }
        }
        return null;
    }


    public static String TEXTS[] = {"北京市","天津市","上海市","重庆市","河北省","山西省","辽宁省","吉林省","黑龙江省","江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省","广东省","海南省","四川省","贵州省","云南省","陕西省","甘肃省","青海省","台湾省","内蒙古自治区","广西壮族自治区","西藏自治区","宁夏回族自治区","新疆维吾尔自治区","香港特别行政区","澳门特别行政区"};
}
