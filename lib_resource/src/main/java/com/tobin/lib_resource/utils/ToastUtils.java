package com.tobin.lib_resource.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tobin.lib_core.base.Box;
import com.tobin.lib_resource.R;

import java.lang.reflect.Field;

/**
 * Created by Tobin on 2021/3/12
 * Email: 616041023@qq.com
 * Description: 自定义吐司工具类
 */
public class ToastUtils {
    // Toast 时间
    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;

    // Toast 类型
    public static final int SUCCESS = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;
    public static final int CONFUSING = 4;
    public static final int INFO = 5;
    public static final int DEFAULT = 6;

    // Toast动画
    public static final int TOP_DOWN = 1;
    public static final int LEFT_RIGHT = 2;
    public static final int SCALE = 3;

    private volatile static BaseToast mToast;

    private static BaseToast instance(int animation) {
        if (mToast == null) {
            synchronized (BaseToast.class) {
                if (mToast == null)
                    mToast = new BaseToast(Box.getApp(), animation);
            }
        }
        mToast.init();
        return mToast;
    }

    public static void makeText(String msg) {
        makeText(msg, LENGTH_SHORT, DEFAULT, 0);
    }

    public static void makeText(String msg, int length) {
        makeText(msg, length, DEFAULT, 0);
    }

    /**
     * no pop Anim
     *
     * @param msg    吐司消息
     * @param length LENGTH_SHORT or LENGTH_LONG
     * @param type   SUCCESS,WARNING,ERROR,CONFUSING,INFO,DEFAULT
     */
    public static void makeText(String msg, int length, int type) {
        makeText(msg, length, type, 0);
    }

    /**
     * @param msg      吐司消息
     * @param length   显示时间长度
     * @param type     6 types
     * @param showAnim animStyle  默认无(TOP_DOWN, LEFT_RIGHT, SCALE)
     */
    public static void makeText(String msg, int length, int type, int showAnim) {
        if (showAnim == TOP_DOWN) {
//            mToast = instance(R.style.toastTopDown);
            mToast = instance(R.style.DefaultAnimation);
        }else if (showAnim == LEFT_RIGHT) {
//            mToast = instance(R.style.toastLeftRight);
            mToast = instance(R.style.DefaultAnimation);
        } else if (showAnim == SCALE) {
//            mToast = instance(R.style.toastScale);
            mToast = instance(R.style.DefaultAnimation);
        } else {
            mToast = instance(0);
        }

        @SuppressLint("InflateParams")
        View layout = LayoutInflater.from(Box.getApp()).inflate(R.layout.custom_toast_layout, null, false);

        ImageView icon = layout.findViewById(R.id.toastIcon);
        TextView text = layout.findViewById(R.id.toastMessage);
        text.setText(msg);

        mToast.setDuration(length);
        switch (type) {
            case SUCCESS:
            case WARNING:
            case ERROR:
            case CONFUSING:
            case INFO:
            case DEFAULT:
            default:
                icon.setImageResource(R.mipmap.toast_default_icon);
                break;
        }
        mToast.setView(layout);
        mToast.show();
    }

    private static class BaseToast extends Toast {

        boolean hasReflectException = false;//是否反射成功
        int mAnim = 0; //动画配置 默认无


        public void setAnim(int AnimStyle) {
            mAnim = AnimStyle;
        }

        public BaseToast(Context context, int AnimStyle) {
            super(context.getApplicationContext());
            setAnim(AnimStyle);
            init();
        }

        public void init() {
            //反射成功调用动画
            if (!hasReflectException) initTN();
        }

        @SuppressLint("SoonBlockedPrivateApi")
        private void initTN() {
            Field mTN;
            Object mObj;
            Class<Toast> clazz = Toast.class;
            int anim = getAnim();
            try {
                mTN = clazz.getDeclaredField("mTN");
                mTN.setAccessible(true);
                mObj = mTN.get(this);

                // 替换默认Toast弹出动画
                Field field = mObj.getClass().getDeclaredField("mParams");
                field.setAccessible(true);
                Object mParams = field.get(mObj);
                if (mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    params.windowAnimations = anim;
                }
                hasReflectException = false;
            } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
                hasReflectException = true;
            }
        }

        public int getAnim() {
            return mAnim;
        }
    }
}
