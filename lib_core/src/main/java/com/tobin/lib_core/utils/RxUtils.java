package com.tobin.lib_core.utils;

import com.tobin.lib_core.http.exception.ErrorTransformer;
import com.tobin.lib_core.http.model.BaseModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RxUtils {

    static {
        RxJavaPlugins.setErrorHandler(throwable -> {
            throwable.printStackTrace();
            Timber.tag("RxUtils").d("RxJavaPlugins throwable: %s", throwable.getMessage());
        });
    }

    /**
     * 默认回调到主线程
     */
    public static <T> ObservableTransformer<BaseModel<T>, T> httpResponseTransformer() {
        return new ObservableTransformer<BaseModel<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseModel<T>> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.newThread())
                        .compose(ErrorTransformer.<T>getInstance())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 自己决定是否回调到主线程
     *
     * @param isObserveOnMain boolean
     * @param <T>             T
     * @return <R>Observable<T>
     */
    public static <T> ObservableTransformer<BaseModel<T>, T> httpResponseTransformer(final boolean isObserveOnMain) {
        return new ObservableTransformer<BaseModel<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseModel<T>> upstream) {
                if (isObserveOnMain) {
                    return upstream.subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.newThread())
                            .compose(ErrorTransformer.<T>getInstance())
                            .observeOn(AndroidSchedulers.mainThread());
                }
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.newThread())
                        .compose(ErrorTransformer.<T>getInstance());
            }
        };
    }

    public static Observable<Integer> rxCountDown(int interval, final int times) {
        return Observable.interval(0, interval, TimeUnit.SECONDS)
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        return times - aLong.intValue();
                    }
                })
                .take(times + 1);
    }

}
