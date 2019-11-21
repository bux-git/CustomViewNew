package com.zx;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.TypedValue;

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

        options.inSampleSize =1;
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
}
