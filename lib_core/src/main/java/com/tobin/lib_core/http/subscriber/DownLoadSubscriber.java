package com.tobin.lib_core.http.subscriber;

import com.tobin.lib_core.http.exception.ApiException;

import timber.log.Timber;

public abstract class DownLoadSubscriber extends BaseSubscriber {

    @Override
    public void onNext(Object o) {
        if (o instanceof Integer) {
            _onProgress((Integer) o);
        }

        if (o instanceof String) {
            _onNext((String) o);
        }
        mSubscription.request(1);
    }

    @Override
    protected void onError(ApiException ex) {
        Timber.e(ex.message+":"+ex.code);
    }

    protected abstract void _onNext(String result);

    protected abstract void _onProgress(Integer percent);

}
