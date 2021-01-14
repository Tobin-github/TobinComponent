package com.tobin.lib_core.http.observer;

import com.tobin.lib_core.http.exception.ApiException;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public abstract class CommonObserver<T> extends BaseObserver<T> {
    private static final String TAG = "CommonObserver";
    private int emptyCode;

    public CommonObserver() {
    }

    public CommonObserver(int emptyDataCode) {
        this.emptyCode = emptyDataCode;
    }

    @Override
    protected void onError(ApiException ex) {
        Timber.tag(TAG).e(ex.message + ":" + ex.code);
        if (emptyCode != 0 && ex.code == emptyCode) {
            onEmptyData();
        }
    }

    @Override
    protected void onNetError() {
        Timber.tag(TAG).w("当前无网络，请检查网络情况");
    }

    @Override
    public abstract void onNext(@NotNull T t);

    @Override
    public void onComplete() {

    }

    protected void onEmptyData() {

    }
}
