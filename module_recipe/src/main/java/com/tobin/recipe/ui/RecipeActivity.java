package com.tobin.recipe.ui;

import android.content.Intent;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseActivity;
import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.recipe.R;
import com.tobin.recipe.bean.RecipesClassBean;
import com.tobin.recipe.databinding.RecipeActivityMainBinding;

@Route(path = RouterHub.RECIPE_RECIPE_ACTIVITY)
public class RecipeActivity extends BaseActivity<RecipeActivityViewModel, RecipeActivityMainBinding> {
    private  RecipesClassBean.ResultBean.ListBean listBean;
    public static final String INTENT_DATA = "class_id";

    @Override
    protected int onCreate() {
        return R.layout.recipe_activity_main;
    }

    @Override
    protected void initView() {
        Intent intent =getIntent();
        listBean = (RecipesClassBean.ResultBean.ListBean) intent.getSerializableExtra(INTENT_DATA);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BaseFragment fragment;
        if (listBean != null){
            fragment = RecipeResultFragment.newInstance(listBean);
        }else {
            fragment = new RecipeClassFragment();
        }
        transaction.replace(R.id.fragment_container, fragment);
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