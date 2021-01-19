package com.tobin.lib_resource.mvvm.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionFragment;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.tobin.lib_core.http.exception.ApiException;
import com.tobin.lib_core.http.exception.ErrorType;
import com.tobin.lib_resource.lifecycle.BaseViewModel;
import com.tobin.lib_resource.loadsir.ErrorCallback;
import com.tobin.lib_resource.loadsir.LottieEmptyCallback;
import com.tobin.lib_resource.loadsir.LottieLoadingCallback;
import com.tobin.lib_resource.loadsir.NetErrorCallback;

import timber.log.Timber;

/**
 * Created by Tobin on 2020/12/22
 * Email: 616041023@qq.com
 * Description: mvvm Fragment基类
 */
public abstract class BaseFragment<VM extends BaseViewModel, DB extends ViewDataBinding> extends SimpleImmersionFragment {
    protected VM viewModel;
    protected DB dataBinding;
    protected Activity activity;

    protected LoadService loadService;
    private boolean visibleToUser;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = initViewModel();

        Timber.tag("Tobin").i("BaseFragment onViewCreated");

        initView(view);
        if (!isLazyLoad()){
            initData();
        }
    }

    /**
     * 初始化视图
     */
    protected abstract void initView(View view);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onResume() {
        super.onResume();
        if (!visibleToUser && isLazyLoad()) {
            visibleToUser = true;
            initData();
        }
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true).init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dataBinding != null) {
            dataBinding.unbind();
        }
    }

    protected void showSuccess() {
        if (loadService != null) {
            loadService.showSuccess();
        }
    }

    public void showEmpty() {
        if (loadService != null) {
            loadService.showCallback(LottieEmptyCallback.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = initDataBinding(inflater, onCreate(), container);
        loadService = LoadSir.getDefault().register(dataBinding.getRoot(), new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                // 重新加载逻辑
                Timber.tag("Tobin").e("BaseFragment LoadSir --> getDefault: onReload");
                loadService.showCallback(LottieLoadingCallback.class);
                initData();
            }
        });
        return loadService.getLoadLayout();
    }

    protected DB initDataBinding(LayoutInflater inflater, int layoutId, ViewGroup container) {
        DB db = DataBindingUtil.inflate(inflater, layoutId, container, false);
        /**
         * 将这两个初始化函数插在{@link BaseFragment#initDataBinding}
         */
        viewModel = initViewModel();
        initObserve();
        return db;
    }

    /**
     * 初始化要加载的布局资源ID
     */
    protected abstract int onCreate();

    /**
     * 初始化ViewModel
     */
    protected abstract VM initViewModel();

    /**
     * 懒加载，只有在Fragment第一次创建且第一次对用户可见
     */
    protected boolean isLazyLoad(){
        return true;
    }

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
                    Timber.tag("Tobin").i("ApiException message: " + exception.message + " code: " + exception.code);
                    if ((exception.code == ErrorType.NETWORD_ERROR) && loadService != null){
                        loadService.showCallback(NetErrorCallback.class);
                    }else{
                        showError(exception.message);
                        loadService.showCallback(ErrorCallback.class);
                    }
                } else {
                    showError(throwable.getMessage());
                    loadService.showCallback(ErrorCallback.class);
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
