package com.tobin.mine;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tobin.lib_resource.arouter.RouterHub;

import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig;
import com.tobin.mine.databinding.MineFragmentMineBinding;

@Route(path = RouterHub.APP_MINE_FRAGMENT)
public class MineFragment extends BaseFragment {
    MineFragmentMineBinding binding;
    private MineViewModel mineViewModel;

    protected void initData() {
        mineViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
    }

    @Override
    protected void initViewModel() {
        mineViewModel = getFragmentScopeViewModel(MineViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.mine_fragment_mine, BR.vm, mineViewModel)
                .addBindingParam(BR.click, new ClickProxy());
    }

    public class ClickProxy {

    }

}