package com.tobin.recipe.ui.detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;

import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig;
import com.tobin.recipe.BR;
import com.tobin.recipe.R;
import com.tobin.recipe.ui.ShareViewModel;

public class DetailFragment extends BaseFragment {

    private DetailViewModel viewModel;
    private ShareViewModel shareViewModel;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    protected void initViewModel() {
        viewModel = getFragmentScopeViewModel(DetailViewModel.class);
        shareViewModel = getActivityScopeViewModel(ShareViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.recipe_fragment_detail, BR.vm, viewModel);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}