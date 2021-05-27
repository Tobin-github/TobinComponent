package com.tobin.life;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tobin.lib_resource.app.AppStore;
import com.tobin.lib_resource.arouter.RouterHub;

import com.tobin.lib_resource.mvvm.base.BaseActivity;
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig;
import com.tobin.life.databinding.ActivityMainBinding;
import com.tobin.recipe.BR;

@Route(path = RouterHub.APP_MAIN_ACTIVITY)
public class MainActivity extends BaseActivity {
    public static final int Tag_Fragment_Home = 0;
    public static final int Tag_Fragment_Recipe = 1;
    public static final int Tag_Fragment_Mine = 2;
    public static final int Tag_Fragment_Video = 3;
    private ActivityMainBinding dataBinding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = (ActivityMainBinding) getBinding();
        initView();
        initData();
    }

    protected void initView() {
        dataBinding.navigationTabBar
                .setIdContainer(R.id.container)
                .setFragmentManager(getSupportFragmentManager());

        dataBinding.navigationTabBar
                .addNavigationItem(R.drawable.ic_home_black_24dp, R.drawable.ic_home_black_24dp,
                        createFragmentWithRouter(RouterHub.APP_HOME_FRAGMENT), Tag_Fragment_Home, "首页")
                .addNavigationItem(R.drawable.ic_dashboard_black_24dp, R.drawable.ic_dashboard_black_24dp,
                        createFragmentWithRouter(RouterHub.APP_VIDEO_FRAGMENT), Tag_Fragment_Video, "发现")
                .addNavigationItem(R.drawable.ic_notifications_black_24dp, R.drawable.ic_notifications_black_24dp,
                        createFragmentWithRouter(RouterHub.APP_MINE_FRAGMENT), Tag_Fragment_Mine, "我的");
        dataBinding.navigationTabBar.init();
    }

    protected void initData() {
        AppStore.mine.observe(this, integer -> {
            dataBinding.navigationTabBar.getNavigationItem(Tag_Fragment_Mine).setRedpotViewVisible(true);
        });
        dataBinding.navigationTabBar.getNavigationItem(Tag_Fragment_Mine).setRedpotViewVisible(true);
    }

    /**
     * 创建路由Fragment
     */
    public Fragment createFragmentWithRouter(String path) {
        return (Fragment) ARouter.getInstance().build(path).navigation();
    }

    @Override
    protected void initViewModel() {
        mainViewModel = getActivityScopeViewModel(MainViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_main, BR.vm, mainViewModel);
    }

}