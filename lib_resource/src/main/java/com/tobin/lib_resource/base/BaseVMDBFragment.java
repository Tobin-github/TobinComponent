package com.tobin.lib_resource.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.tobin.lib_core.http.exception.ApiException;
import com.tobin.lib_core.http.exception.ErrorType;
import com.tobin.lib_resource.lifecycle.BaseViewModel;
import com.tobin.lib_resource.loadsir.LottieLoadingCallback;
import com.tobin.lib_resource.loadsir.TimeoutCallback;

import timber.log.Timber;

/**
 * Created by Tobin on 2020/12/22
 * Email: 616041023@qq.com
 * Description:
 */
public abstract class BaseVMDBFragment<VM extends BaseViewModel, DB extends ViewDataBinding> extends BaseDBFragment<DB> {
    protected VM viewModel;

    protected LoadService loadService;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = initViewModel();
        initObserve();
        Timber.tag("libCore").i("BaseFragment onViewCreated");

        loadService = LoadSir.getDefault().register(view, (Callback.OnReloadListener) v -> {
            Timber.tag("BaseVMDBFragment").w("loadService register");
//            loadService.showCallback(LottieLoadingCallback.class);
            initData();
        });
    }

    @Override
    protected DB initDataBinding(LayoutInflater inflater, int layoutId, ViewGroup container) {
        DB db = super.initDataBinding(inflater, layoutId, container);
        /**
         * 将这两个初始化函数插在{@link BaseVMDBFragment#initDataBinding}
         */
        viewModel = initViewModel();
        initObserve();
        return db;
    }

    /**
     * 初始化ViewModel
     */
    protected abstract VM initViewModel();

    /**
     * 监听当前ViewModel中 showDialog和error的值
     */
    private void initObserve() {
        if (viewModel == null) return;
        viewModel.getError(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if (throwable instanceof ApiException) {
                    ApiException exception = (ApiException) throwable;
                    Timber.tag("Tobin").i("message" + exception.message + "code: " + exception.code);
                    if (exception.code == ErrorType.NETWORD_ERROR){
                        loadService.showCallback(TimeoutCallback.class);
                    }else {
                        showError(exception.getMessage());
                    }
                } else {
                    Timber.tag("Tobin").e("throwable message: %s", throwable.getMessage());
                }
            }
        });
    }

    /**
     * ViewModel层发生了错误
     */
    protected abstract void showError(Object obj);
}
