package com.tobin.lib_core.http.observer;

import androidx.annotation.CallSuper;

import com.tobin.lib_core.http.exception.ApiException;
import com.tobin.lib_core.http.exception.ErrorType;
import com.tobin.lib_core.utils.NetworkUtils;

import io.reactivex.observers.DefaultObserver;
import timber.log.Timber;

public abstract class BaseObserver<T> extends DefaultObserver<T> {
    private static final String TAG = "BaseObserver";

    @CallSuper
    @Override
    protected void onStart() {
        super.onStart();
        Timber.d("onStart: ");
        if (!NetworkUtils.isNetworkAvailable()) {
            Timber.tag(TAG).d("network not available");
            onNetError();
            onComplete();
            // 无网络执行complete后取消注册防调用onError
            // 但是会导致doOnTerminate和doAfterTerminate都不会执行
            cancel();
        } else {
            Timber.tag(TAG).d("network available");
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, ErrorType.UNKNOWN));
        }
    }

    /**
     * 错误回调
     */
    protected abstract void onError(ApiException ex);

    /**
     * 网络错误
     */
    protected abstract void onNetError();
}
