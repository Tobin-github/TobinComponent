package com.tobin.lib_resource.base;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.tobin.lib_core.http.exception.ApiException;
import com.tobin.lib_resource.lifecycle.BaseViewModel;

import timber.log.Timber;

/**
 * Created by Tobin on 2020/12/22
 * Email: 616041023@qq.com
 * Description:
 */
public abstract class BaseVMDBActivity<VM extends BaseViewModel, DB extends ViewDataBinding> extends BaseDBActivity<DB> {

    protected VM viewModel;

    @Override
    protected void onCreate(@androidx.annotation.Nullable android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel();
        initObserve();
    }

    /**
     * 初始化ViewModel
     */
    protected abstract VM initViewModel();

    /**
     * 监听当前ViewModel中变量
     */
    private void initObserve() {
        if (viewModel == null) return;
        viewModel.getError(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if (throwable instanceof ApiException) {
                    ApiException exception = (ApiException) throwable;
                    showError(exception.getMessage());
                } else {
                    Timber.tag("BaseVMDBActivity").e("throwable message: %s", throwable.getMessage());
                }
            }
        });
    }

    /**
     * ViewModel层发生了错误
     */
    protected abstract void showError(Object obj);
}
