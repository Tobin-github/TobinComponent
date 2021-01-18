package com.tobin.mine;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.mine.databinding.FragmentMineBinding;

@Route(path = RouterHub.APP_MINE_FRAGMENT)
public class MineFragment extends BaseFragment<MineViewModel, FragmentMineBinding> {

    @Override
    protected int onCreate() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                dataBinding.textNotifications.setText(s);
            }
        });
    }

    @Override
    protected MineViewModel initViewModel() {
        return new ViewModelProvider(this).get(MineViewModel.class);
    }

    @Override
    protected void showError(Object obj) {

    }
}