package com.tobin.lib_resource.base;

import androidx.databinding.ViewDataBinding;

import com.tobin.lib_resource.lifecycle.BaseViewModel;

public abstract class BaseActivity<VM extends BaseViewModel, DB extends ViewDataBinding> extends BaseNoModelActivity<DB> {

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
        viewModel.getError(this, this::showError);
    }

    /**
     * ViewModel层发生了错误
     */
    protected abstract void showError(Object obj);
}
