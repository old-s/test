package com.demo.ctw.utils.picker;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * 兼容旧版&新版
 *
 * @author 李玉江[QQ :1032694760]
 * @since 2015 /10/19 Created By Android Studio
 */
public class CompatUtils {

    /**
     * dp转换为px
     *
     * @param context the context
     * @param dpValue the dp value
     * @return int int
     */
    public static int toPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int pxValue = (int) (dpValue * scale + 0.5f);
        return pxValue;
    }

    /**
     * Sets background.
     *
     * @param view     the view
     * @param drawable the drawable
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT < 16) {
            //noinspection deprecation
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    /**
     * px转换为dp
     *
     * @param context the context
     * @param pxValue the px value
     * @return int int
     */
    public static int toDp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int dpValue = (int) (pxValue / scale + 0.5f);
        return dpValue;
    }
}
