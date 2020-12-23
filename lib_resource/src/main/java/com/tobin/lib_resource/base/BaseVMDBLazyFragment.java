package com.tobin.lib_resource.base;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleObserver;

import com.tobin.lib_resource.lifecycle.BaseViewModel;

/**
 * Created by Tobin on 2020/12/22
 * Email: 616041023@qq.com
 * Description: 懒加载Fragment基类，适用于一个页面多个Tab页面
 */
public abstract class BaseVMDBLazyFragment<VM extends BaseViewModel, DB extends ViewDataBinding> extends BaseVMDBFragment<VM, DB> implements LifecycleObserver {

    private boolean visibleToUser;

    @Override
    public void onResume() {
        super.onResume();
        if (!visibleToUser) {
            visibleToUser = true;
            lazyLoad();
        }
    }

    /**
     * 懒加载，只有在Fragment第一次创建且第一次对用户可见
     */
    protected abstract void lazyLoad();

}
