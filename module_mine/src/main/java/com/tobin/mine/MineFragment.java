package com.tobin.mine;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.mine.databinding.MineFragmentMineBinding;

@Route(path = RouterHub.APP_MINE_FRAGMENT)
public class MineFragment extends BaseFragment<MineViewModel, MineFragmentMineBinding> {

    @Override
    protected int onCreate() {
        return R.layout.mine_fragment_mine;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                dataBinding.textNotifications.setText(s);
                showSuccess();
            }
        });
    }

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected MineViewModel initViewModel() {
        return new ViewModelProvider(this).get(MineViewModel.class);
    }

    @Override
    protected void showError(Object obj) {

    }
}