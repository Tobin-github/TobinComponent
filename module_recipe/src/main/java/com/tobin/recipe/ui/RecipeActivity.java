package com.tobin.recipe.ui;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.base.BaseDBActivity;
import com.tobin.recipe.databinding.ActivityRecipeBinding;
import com.tobin.recipe.R;

@Route(path = RouterHub.RECIPE_RECIPE_ACTIVITY)
public class RecipeActivity extends BaseDBActivity<ActivityRecipeBinding> {

    @Override
    protected int onCreate() {
        return R.layout.activity_recipe;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}