package com.tobin.lib_resource.base;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.gyf.immersionbar.ImmersionBar;
import com.tobin.lib_resource.R;

/**
 * Created by Tobin on 2020/12/22
 * Email: 616041023@qq.com
 * Description: 不需要ViewModel的页面基类
 */
public abstract class BaseDBActivity<DB extends ViewDataBinding> extends BaseActivity {

    protected Context context;
    protected DB dataBinding;

    @Override
    protected void onCreate(@androidx.annotation.Nullable android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        int layoutId = onCreate();
        setContentView(layoutId);

        dataBinding = initDataBinding(layoutId);
        initView();
        initData();

        initImmersionBar();
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
}