package com.tobin.recipe.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseActivity;
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig;
import com.tobin.recipe.BR;
import com.tobin.recipe.R;

@Route(path = RouterHub.RECIPE_RECIPE_ACTIVITY)
public class RecipeActivity extends BaseActivity {
    private RecipeActivityViewModel recipeActivityViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initViewModel() {
        recipeActivityViewModel = getActivityScopeViewModel(RecipeActivityViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.recipe_activity_main, BR.vm, recipeActivityViewModel);
    }

}