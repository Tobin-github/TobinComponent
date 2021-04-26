package com.tobin.lib_resource.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.tobin.lib_core.base.App;
import com.tobin.lib_core.base.Box;

import timber.log.Timber;

public class ScreenUtils {
    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                App.getApp().getResources().getDisplayMetrics()));
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxValue) {
        final float scale = App.getApp().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
//        final float scale = App.getApp().getResources().getDisplayMetrics().density;
//        return (pxValue / scale);

    }

    /**
     * 将px值转换为sp值
     */
    public static int px2sp(float pxValue) {
        final float fontScale = App.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     *
     * @param spValue float
     */
    public static int sp2px(float spValue) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
                App.getApp().getResources().getDisplayMetrics()));
    }

    /**
     * 获取屏幕的宽度
     */
    public static int getScreenW() {
        DisplayMetrics displayMetrics = App.getApp().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕的高度
     */
    public static int getScreenH() {
        DisplayMetrics displayMetrics = App.getApp().getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 得到设备的密度
     */
    public static float getScreenDensity() {
        return App.getApp().getResources().getDisplayMetrics().density;
    }


    public static void getScreenRelatedInformation(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(outMetrics);
            int widthPixels = outMetrics.widthPixels;
            int heightPixels = outMetrics.heightPixels;
            int densityDpi = outMetrics.densityDpi;
            float density = outMetrics.density;
            float scaledDensity = outMetrics.scaledDensity;

            Timber.d("widthPixels = " + widthPixels + ",heightPixels = " + heightPixels + "\n" +
                    ",densityDpi = " + densityDpi + "\n" +
                    ",density = " + density + ",scaledDensity = " + scaledDensity);
        }
    }

    public static void getRealScreenRelatedInformation(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
            int widthPixels = outMetrics.widthPixels;
            int heightPixels = outMetrics.heightPixels;
            int densityDpi = outMetrics.densityDpi;
            float density = outMetrics.density;
            float scaledDensity = outMetrics.scaledDensity;
            //可用显示大小的绝对宽度（以像素为单位）。
            //可用显示大小的绝对高度（以像素为单位）。
            //屏幕密度表示为每英寸点数。
            //显示器的逻辑密度。
            //显示屏上显示的字体缩放系数。
            Timber.d("widthPixels = " + widthPixels + ",heightPixels = " + heightPixels + "\n" +
                    ",densityDpi = " + densityDpi + "\n" +
                    ",density = " + density + ",scaledDensity = " + scaledDensity);
        }
    }


    /**
     * 获取屏幕内容的实际高度
     */
    public static int getScreenRealHeight() {
        int heightPixels;
        WindowManager w = (WindowManager) Box.getApp().getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        heightPixels = metrics.heightPixels;
        // includes window decorations (statusbar bar/navigation bar)
        try {
            android.graphics.Point realSize = new android.graphics.Point();
            Display.class.getMethod("getRealSize", android.graphics.Point.class).invoke(d, realSize);
            heightPixels = realSize.y;
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return heightPixels;
    }

    public static boolean isScreenLandscape() {
        Configuration configuration = App.getApp().getResources().getConfiguration();
        return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}
