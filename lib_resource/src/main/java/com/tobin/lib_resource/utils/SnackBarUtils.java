package com.tobin.lib_resource.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.tobin.lib_resource.R;

import timber.log.Timber;

/**
 * Created by Tobin on 2021/3/12
 * Email: 616041023@qq.com
 * Description:
 */
public class SnackBarUtils {
    //设置SnackBar背景颜色
    private static final int color_info = 0XFF2094F3;
    private static final int color_confirm = 0XFF4CB04E;
    private static final int color_warning = 0XFFFEC005;
    private static final int color_danger = 0XFFF44336;
    //工具类当前持有的SnackBar实例
    private static Snackbar mSnackBar = null;


    private SnackBarUtils() {
        throw new RuntimeException("禁止无参创建实例");
    }

    public SnackBarUtils(@NonNull Snackbar snackbar) {
        this.mSnackBar = snackbar;
    }

    /**
     * 获取 mSnackBar
     *
     * @return SnackBar
     */
    public Snackbar getSnackBar() {
        return mSnackBar;
    }

    /**
     * 初始化SnackBar实例
     * 展示时间:SnackBar.LENGTH_SHORT
     */
    public static SnackBarUtils Short(View view, String message) {
        mSnackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        return new SnackBarUtils(mSnackBar).backColor(0XFF323232);
    }

    /**
     * 初始化Drawable实例
     * 展示时间:SnackBar.LENGTH_LONG
     */
    public static SnackBarUtils Long(View view, String message) {
        mSnackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        return new SnackBarUtils(mSnackBar).backColor(0XFF323232);
    }

    /**
     * 初始化Drawable实例
     * 展示时间:Drawable.LENGTH_INDEFINITE
     */
    public static SnackBarUtils Indefinite(View view, String message) {
        mSnackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        return new SnackBarUtils(mSnackBar).backColor(0XFF323232);
    }

    /**
     * 初始化Drawable实例
     * 展示时间:duration 毫秒
     */
    public static SnackBarUtils Custom(View view, String message, int duration) {
        mSnackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        mSnackBar.setDuration(duration);
        return new SnackBarUtils(mSnackBar).backColor(0XFF323232);
    }

    /**
     * 设置mSnackBar背景色为  color_info
     */
    public SnackBarUtils info() {
        mSnackBar.getView().setBackgroundColor(color_info);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置mSnackBar背景色为 color_confirm
     */
    public SnackBarUtils confirm() {
        mSnackBar.getView().setBackgroundColor(color_confirm);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置SnackBar背景色为 color_warning
     */
    public SnackBarUtils warning() {
        mSnackBar.getView().setBackgroundColor(color_warning);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置SnackBar背景色为 color_warning
     */
    public SnackBarUtils danger() {
        mSnackBar.getView().setBackgroundColor(color_danger);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置SnackBar背景色
     *
     * @param backgroundColor
     */
    public SnackBarUtils backColor(@ColorInt int backgroundColor) {
        mSnackBar.getView().setBackgroundColor(backgroundColor);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置TextView的文字颜色
     */
    public SnackBarUtils messageColor(@ColorInt int messageColor) {
        ((TextView) mSnackBar.getView().findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置Button的文字颜色
     *
     */
    public SnackBarUtils actionColor(@ColorInt int actionTextColor) {
        ((Button) mSnackBar.getView().findViewById(R.id.snackbar_action)).setTextColor(actionTextColor);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置SnackBar背景色 + TextView的文字颜色 + Button的文字颜色
     */
    public SnackBarUtils colors(@ColorInt int backgroundColor, @ColorInt int messageColor, @ColorInt int actionTextColor) {
        mSnackBar.getView().setBackgroundColor(backgroundColor);
        ((TextView) mSnackBar.getView().findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        ((Button) mSnackBar.getView().findViewById(R.id.snackbar_action)).setTextColor(actionTextColor);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置SnackBar背景透明度
     */
    public SnackBarUtils alpha(float alpha) {
        alpha = alpha >= 1.0f ? 1.0f : (Math.max(alpha, 0.0f));
        mSnackBar.getView().setAlpha(alpha);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置SnackBar显示的位置
     */
    public SnackBarUtils gravityFrameLayout(int gravity) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mSnackBar.getView().getLayoutParams().width, mSnackBar.getView().getLayoutParams().height);
        params.gravity = gravity;
        mSnackBar.getView().setLayoutParams(params);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置SnackBar显示的位置,当SnackBar和CoordinatorLayout组合使用的时候
     *
     */
    public SnackBarUtils gravityCoordinatorLayout(int gravity) {
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(mSnackBar.getView().getLayoutParams().width, mSnackBar.getView().getLayoutParams().height);
        params.gravity = gravity;
        mSnackBar.getView().setLayoutParams(params);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置按钮文字内容 及 点击监听
     * {@link Snackbar#setAction(CharSequence, View.OnClickListener)}
     */
    public SnackBarUtils setAction(@StringRes int resId, View.OnClickListener listener) {
        return setAction(getSnackBar().getView().getResources().getText(resId), listener);
    }

    /**
     * 设置按钮文字内容 及 点击监听
     * {@link Snackbar#setAction(CharSequence, View.OnClickListener)}
     */
    public SnackBarUtils setAction(CharSequence text, View.OnClickListener listener) {
        mSnackBar.setAction(text, listener);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置 mSnackBar 展示完成 及 隐藏完成 的监听
     */
    public SnackBarUtils setCallback(Snackbar.Callback setCallback) {
        mSnackBar.setCallback(setCallback);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置TextView左右两侧的图片
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public SnackBarUtils leftAndRightDrawable(@Nullable @DrawableRes Integer leftDrawable, @Nullable @DrawableRes Integer rightDrawable) {
        Drawable drawableLeft = null;
        Drawable drawableRight = null;
        if (leftDrawable != null) {
            try {
                drawableLeft = getSnackBar().getView().getResources().getDrawable(leftDrawable.intValue());
            } catch (Exception e) {
                Timber.e("getSnackBar().getView().getResources().getDrawable(leftDrawable.intValue())");
            }
        }
        if (rightDrawable != null) {
            try {
                drawableRight = getSnackBar().getView().getResources().getDrawable(rightDrawable.intValue());
            } catch (Exception e) {
                Timber.e("getSnackbar().getView().getResources().getDrawable(rightDrawable.intValue())");
            }
        }
        return leftAndRightDrawable(drawableLeft, drawableRight);
    }

    /**
     * 设置TextView左右两侧的图片
     *
     */
    public SnackBarUtils leftAndRightDrawable(@Nullable Drawable leftDrawable, @Nullable Drawable rightDrawable) {
        TextView message = (TextView) mSnackBar.getView().findViewById(R.id.snackbar_text);
        LinearLayout.LayoutParams paramsMessage = (LinearLayout.LayoutParams) message.getLayoutParams();
        paramsMessage = new LinearLayout.LayoutParams(paramsMessage.width, paramsMessage.height, 0.0f);
        message.setLayoutParams(paramsMessage);
        message.setCompoundDrawablePadding(message.getPaddingLeft());
        int textSize = (int) message.getTextSize();
        Timber.e("textSize:%s", textSize);
        if (leftDrawable != null) {
            leftDrawable.setBounds(0, 0, textSize, textSize);
        }
        if (rightDrawable != null) {
            rightDrawable.setBounds(0, 0, textSize, textSize);
        }
        message.setCompoundDrawables(leftDrawable, null, rightDrawable, null);
        LinearLayout.LayoutParams paramsSpace = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        ((Snackbar.SnackbarLayout) mSnackBar.getView()).addView(new Space(mSnackBar.getView().getContext()), 1, paramsSpace);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置TextView中文字的对齐方式 居中
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SnackBarUtils messageCenter() {
        TextView message = mSnackBar.getView().findViewById(R.id.snackbar_text);
        message.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        message.setGravity(Gravity.CENTER);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置TextView中文字的对齐方式 居右
     *
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SnackBarUtils messageRight() {
        TextView message = mSnackBar.getView().findViewById(R.id.snackbar_text);
        message.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        message.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 向SnackBar布局中添加View(Google不建议,复杂的布局应该使用DialogFragment进行展示)
     *
     */
    public SnackBarUtils addView(int layoutId, int index) {
        //加载布局文件新建View
        View addView = LayoutInflater.from(mSnackBar.getView().getContext()).inflate(layoutId, null);
        return addView(addView, index);
    }

    /**
     * 向SnackBar布局中添加View(Google不建议,复杂的布局应该使用DialogFragment进行展示)
     */
    public SnackBarUtils addView(View addView, int index) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);//设置新建布局参数
        //设置新建View在SnackBar内垂直居中显示
        params.gravity = Gravity.CENTER_VERTICAL;
        addView.setLayoutParams(params);
        ((Snackbar.SnackbarLayout) mSnackBar.getView()).addView(addView, index);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置SnackBar布局的外边距
     * 注:经试验发现,调用margins后再调用 gravityFrameLayout,则margins无效.
     * 为保证margins有效,应该先调用 gravityFrameLayout,在 show() 之前调用 margins
     */
    public SnackBarUtils margins(int margin) {
        return margins(margin, margin, margin, margin);
    }

    /**
     * 设置SnackBar布局的外边距
     * 注:经试验发现,调用margins后再调用 gravityFrameLayout,则margins无效.
     * 为保证margins有效,应该先调用 gravityFrameLayout,在 show() 之前调用 margins
     */
    public SnackBarUtils margins(int left, int top, int right, int bottom) {
        ViewGroup.LayoutParams params = mSnackBar.getView().getLayoutParams();
        ((ViewGroup.MarginLayoutParams) params).setMargins(left, top, right, bottom);
        mSnackBar.getView().setLayoutParams(params);
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 经试验发现:
     * 执行过{@link SnackBarUtils#backColor(int)}后:background instanceof ColorDrawable
     * 未执行过{@link SnackBarUtils#backColor(int)}:background instanceof GradientDrawable
     */
    public SnackBarUtils radius() {
        Drawable background = mSnackBar.getView().getBackground();
        if (background instanceof GradientDrawable) {
            Timber.e("radius():GradientDrawable");
        }
        if (background instanceof ColorDrawable) {
            Timber.e("radius():ColorDrawable");
        }
        if (background instanceof StateListDrawable) {
            Timber.e("radius():StateListDrawable");
        }
        Timber.e("radius()background:%s", background.getClass().getSimpleName());
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 通过SnackBar现在的背景,获取其设置圆角值时候所需的GradientDrawable实例
     */
    private GradientDrawable getRadiusDrawable(Drawable backgroundOri) {
        GradientDrawable background = null;
        if (backgroundOri instanceof GradientDrawable) {
            background = (GradientDrawable) backgroundOri;
        } else if (backgroundOri instanceof ColorDrawable) {
            int backgroundColor = ((ColorDrawable) backgroundOri).getColor();
            background = new GradientDrawable();
            background.setColor(backgroundColor);
        }
        return background;
    }

    /**
     * 设置SnackBar布局的圆角半径值
     *
     * @param radius 圆角半径
     */
    public SnackBarUtils radius(float radius) {
        //将要设置给mSnackBar的背景
        GradientDrawable background = getRadiusDrawable(mSnackBar.getView().getBackground());
        if (background != null) {
            radius = radius <= 0 ? 12 : radius;
            background.setCornerRadius(radius);
            mSnackBar.getView().setBackground(background);
        }
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置SnackBar布局的圆角半径值及边框颜色及边框宽度
     */
    public SnackBarUtils radius(int radius, int strokeWidth, @ColorInt int strokeColor) {
        //将要设置给mSnackBar的背景
        GradientDrawable background = getRadiusDrawable(mSnackBar.getView().getBackground());
        if (background != null) {
            radius = radius <= 0 ? 12 : radius;
            strokeWidth = strokeWidth <= 0 ? 1 : (strokeWidth >= mSnackBar.getView().findViewById(R.id.snackbar_text).getPaddingTop() ? 2 : strokeWidth);
            background.setCornerRadius(radius);
            background.setStroke(strokeWidth, strokeColor);
            mSnackBar.getView().setBackgroundDrawable(background);
        }
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 计算单行的SnackBar的高度值(单位 pix)
     */
    private int calculateSnackBarHeight() {
        int snackBarHeight = ScreenUtils.dp2px(28) + ScreenUtils.sp2px(14);
        Timber.e("直接获取MessageView高度:%s", mSnackBar.getView().findViewById(R.id.snackbar_text).getHeight());
        return snackBarHeight;
    }

    /**
     * 设置SnackBar显示在指定View的上方
     * 注:暂时仅支持单行的SnackBar,因为{@link SnackBarUtils#calculateSnackBarHeight()}暂时仅支持单行SnackBar的高度计算
     *
     * @param targetView     指定View
     * @param contentViewTop Activity中的View布局区域 距离屏幕顶端的距离
     * @param marginLeft     左边距
     * @param marginRight    右边距
     */
    public SnackBarUtils above(View targetView, int contentViewTop, int marginLeft, int marginRight) {
        marginLeft = Math.max(marginLeft, 0);
        marginRight = Math.max(marginRight, 0);
        int[] locations = new int[2];
        targetView.getLocationOnScreen(locations);
        Timber.e("距离屏幕左侧:" + locations[0] + "==距离屏幕顶部:" + locations[1]);
        int snackbarHeight = calculateSnackBarHeight();
        Timber.e("SnackBar高度:%s", snackbarHeight);
        //必须保证指定View的顶部可见 且 单行SnackBar可以完整的展示
        if (locations[1] >= contentViewTop + snackbarHeight) {
            gravityFrameLayout(Gravity.BOTTOM);
            ViewGroup.LayoutParams params = mSnackBar.getView().getLayoutParams();
            ((ViewGroup.MarginLayoutParams) params).setMargins(marginLeft, 0, marginRight, mSnackBar.getView().getResources().getDisplayMetrics().heightPixels - locations[1]);
            mSnackBar.getView().setLayoutParams(params);
        }
        return new SnackBarUtils(mSnackBar);
    }

    /**
     * 设置Snackbar显示在指定View的下方
     * 注:暂时仅支持单行的Snackbar,因为{@link SnackBarUtils#calculateSnackBarHeight()}暂时仅支持单行Snackbar的高度计算
     *
     * @param targetView     指定View
     * @param contentViewTop Activity中的View布局区域 距离屏幕顶端的距离
     * @param marginLeft     左边距
     * @param marginRight    右边距
     */
    public SnackBarUtils bellow(View targetView, int contentViewTop, int marginLeft, int marginRight) {
        marginLeft = Math.max(marginLeft, 0);
        marginRight = Math.max(marginRight, 0);
        int[] locations = new int[2];
        targetView.getLocationOnScreen(locations);
        int snackbarHeight = calculateSnackBarHeight();
        int screenHeight = ScreenUtils.getScreenRealHeight();
        //必须保证指定View的底部可见 且 单行Snackbar可以完整的展示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //为什么要'+2'? 因为在Android L(Build.VERSION_CODES.LOLLIPOP)以上,例如Button会有一定的'阴影(shadow)',阴影的大小由'高度(elevation)'决定.
            //为了在Android L以上的系统中展示的Snackbar不要覆盖targetView的阴影部分太大比例,所以人为减小2px的layout_marginBottom属性.
            if (locations[1] + targetView.getHeight() >= contentViewTop && locations[1] + targetView.getHeight() + snackbarHeight + 2 <= screenHeight) {
                gravityFrameLayout(Gravity.BOTTOM);
                ViewGroup.LayoutParams params = mSnackBar.getView().getLayoutParams();
                ((ViewGroup.MarginLayoutParams) params).setMargins(marginLeft, 0, marginRight, screenHeight - (locations[1] + targetView.getHeight() + snackbarHeight + 2));
                mSnackBar.getView().setLayoutParams(params);
            }
        } else {
            if (locations[1] + targetView.getHeight() >= contentViewTop && locations[1] + targetView.getHeight() + snackbarHeight <= screenHeight) {
                gravityFrameLayout(Gravity.BOTTOM);
                ViewGroup.LayoutParams params = mSnackBar.getView().getLayoutParams();
                ((ViewGroup.MarginLayoutParams) params).setMargins(marginLeft, 0, marginRight, screenHeight - (locations[1] + targetView.getHeight() + snackbarHeight));
                mSnackBar.getView().setLayoutParams(params);
            }
        }
        return new SnackBarUtils(mSnackBar);
    }


    /**
     * 显示 mSnackBar
     */
    public void show() {
        if (mSnackBar != null) {
            mSnackBar.show();
        }
    }

}
