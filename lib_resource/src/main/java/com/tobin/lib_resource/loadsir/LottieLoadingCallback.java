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
}
