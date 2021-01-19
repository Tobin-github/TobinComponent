package com.tobin.life;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tobin.lib_resource.app.AppStore;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseActivity;
import com.tobin.life.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {
    public static final int Tag_Fragment_Home = 0;
    public static final int Tag_Fragment_Recipe = 1;
    public static final int Tag_Fragment_Mine = 2;
    public static final int Tag_Fragment_Service = 3;

    @Override
    protected int onCreate() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        dataBinding.navigationTabBar
                .setIdContainer(R.id.container)
                .setFragmentManager(getSupportFragmentManager());

        dataBinding.navigationTabBar
                .addNavigationItem(R.drawable.ic_home_black_24dp, R.drawable.ic_home_black_24dp,
                        createFragmentWithRouter(RouterHub.APP_HOME_FRAGMENT), Tag_Fragment_Home, "首页")
                .addNavigationItem(R.drawable.ic_dashboard_black_24dp, R.drawable.ic_dashboard_black_24dp,
                        createFragmentWithRouter(RouterHub.RECIPE_RECIPE_FRAGMENT), Tag_Fragment_Recipe, "发现")
                .addNavigationItem(R.drawable.ic_notifications_black_24dp, R.drawable.ic_notifications_black_24dp,
                        createFragmentWithRouter(RouterHub.APP_MINE_FRAGMENT), Tag_Fragment_Mine, "我的");

        dataBinding.navigationTabBar.init();
    }

    @Override
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
        Fragment fragment =  (Fragment) ARouter.getInstance().build(path).navigation();
        return fragment;
    }

    @Override
    protected MainViewModel initViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    protected void showError(Object obj) {

    }
}