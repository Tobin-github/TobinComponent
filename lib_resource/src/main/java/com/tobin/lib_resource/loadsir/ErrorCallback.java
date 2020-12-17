package com.tobin.lib_resource.loadsir;

import com.kingja.loadsir.callback.Callback;

import com.tobin.lib_resource.R;

public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_error;
    }
}
