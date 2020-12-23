package com.tobin.life;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.tobin.lib_resource.base.BaseDBActivity;
import com.tobin.library.navigation.view.EasyNavigationBar;
import com.tobin.life.databinding.ActivityMainBinding;
import com.tobin.life.ui.dashboard.DashboardFragment;
import com.tobin.life.ui.home.HomeFragment;
import com.tobin.life.ui.notifications.NotificationsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseDBActivity<ActivityMainBinding> {
    private EasyNavigationBar navigationBar;
    private List<Fragment> fragments = new ArrayList<>();

    private String[] tabText = {"首页", "发现", "我的"};
    //未选中icon
    private int[] normalIcon = {R.drawable.ic_home_black_24dp, R.drawable.ic_dashboard_black_24dp, R.drawable.ic_notifications_black_24dp};
    //选中时icon
    private int[] selectIcon = {R.drawable.ic_home_black_24dp, R.drawable.ic_dashboard_black_24dp, R.drawable.ic_notifications_black_24dp};
    @Override
    protected int onCreate() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        navigationBar = findViewById(R.id.navigationBar);

        fragments.add(new HomeFragment());
        fragments.add(new DashboardFragment());
        fragments.add(new NotificationsFragment());

        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .canScroll(true)
                .build();

        navigationBar.setOnTabClickListener(new EasyNavigationBar.OnTabClickListener() {

            @Override
            public boolean onTabSelectEvent(View view, int position) {
//                tv_content.setText("您点击了第"+position+"个Tab，这里面没有Fragment的、只是单纯的点击");
                return false;
            }

            @Override
            public boolean onTabReSelectEvent(View view, int position) {
                Toast.makeText(MainActivity.this,"重复点击",Toast.LENGTH_SHORT).show();
                return false;
            }

        });
    }

    @Override
    protected void initData() {

    }

}