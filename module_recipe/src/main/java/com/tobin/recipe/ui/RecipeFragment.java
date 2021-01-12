package com.tobin.recipe.ui;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.base.BaseVMDBFragment;
import com.tobin.recipe.R;
import com.tobin.recipe.databinding.RecipeFragmentBinding;

@Route(path = RouterHub.RECIPE_RECIPE_FRAGMENT)
public class RecipeFragment extends BaseVMDBFragment<RecipeViewModel, RecipeFragmentBinding> {

    @Override
    protected int onCreate() {
        return R.layout.recipe_fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected RecipeViewModel initViewModel() {
        return new ViewModelProvider(this).get(RecipeViewModel.class);
    }

    @Override
    protected void showError(Object obj) {

    }
}