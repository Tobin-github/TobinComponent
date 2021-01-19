package com.tobin.lib_resource.loadsir;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;

import com.tobin.lib_resource.R;

public class LottieLoadingCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_lottie_loading;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }

    //是否在显示Callback视图的时候显示原始图(SuccessView)，返回true显示，false隐藏
    @Override
    public boolean getSuccessVisible() {
        return super.getSuccessVisible();
    }
}
