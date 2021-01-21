package com.tobin.recipe.ui;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.tobin.lib_resource.mvvm.base.BaseActivity;
import com.tobin.recipe.R;
import com.tobin.recipe.databinding.RecipeActivitySearchBinding;

public class SearchActivity extends BaseActivity<SearchViewModel, RecipeActivitySearchBinding>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int onCreate() {
        return R.layout.recipe_activity_search;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected SearchViewModel initViewModel() {
        return new ViewModelProvider(this).get(SearchViewModel.class);
    }

    @Override
    protected void showError(Object obj) {

    }
}