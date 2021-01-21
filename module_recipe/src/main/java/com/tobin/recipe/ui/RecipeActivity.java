package com.tobin.recipe.ui;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseActivity;
import com.tobin.recipe.R;
import com.tobin.recipe.databinding.RecipeActivityMainBinding;

@Route(path = RouterHub.RECIPE_RECIPE_ACTIVITY)
public class RecipeActivity extends BaseActivity<RecipeActivityViewModel, RecipeActivityMainBinding> {

    @Override
    protected int onCreate() {
        return R.layout.recipe_activity_main;
    }

    @Override
    protected void initView() {


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        RecipeClassFragment mineFragment = new RecipeClassFragment();
        transaction.replace(R.id.fragment_container, mineFragment);
        transaction.commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected RecipeActivityViewModel initViewModel() {
        return new ViewModelProvider(this).get(RecipeActivityViewModel.class);
    }

    @Override
    protected void showError(Object obj) {

    }
}