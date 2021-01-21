package com.tobin.lib_resource.mvvm.base;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.gyf.immersionbar.ImmersionBar;
import com.tobin.lib_core.http.exception.ApiException;
import com.tobin.lib_resource.R;
import com.tobin.lib_resource.lifecycle.BaseViewModel;

import timber.log.Timber;

/**
 * Created by Tobin on 2020/12/22
 * Email: 616041023@qq.com
 * Description:
 */
public abstract class BaseActivity<VM extends BaseViewModel, DB extends ViewDataBinding> extends AppCompatActivity {

    protected VM viewModel;
    protected DB dataBinding;

    @Override
    protected void onCreate(@androidx.annotation.Nullable android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = onCreate();
        if (isShowToolbar()){

        }
        setContentView(layoutId);

        dataBinding = initDataBinding(layoutId);
        initView();
        initData();

        initImmersionBar();

        viewModel = initViewModel();
        initObserve();
    }

    protected boolean isShowToolbar() {
        return false;
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        ImmersionBar.with(this).titleBar(R.id.toolbar_layout).statusBarDarkFont(true).init();
    }

    /**
     * 初始化要加载的布局资源ID
     * 此函数优先执行于onCreate()可以做window操作
     */
    protected abstract int onCreate();

    /**
     * 初始化DataBinding
     */
    protected DB initDataBinding(@LayoutRes int layoutId) {
        return DataBindingUtil.setContentView(this, layoutId);
    }

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onDestroy() {
        if (dataBinding != null) {
            dataBinding.unbind();
        }
        super.onDestroy();
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
