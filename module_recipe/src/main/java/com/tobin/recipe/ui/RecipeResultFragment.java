package com.tobin.recipe.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.tobin.lib_resource.arouter.RouterHub;

import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig;
import com.tobin.recipe.BR;
import com.tobin.recipe.R;
import com.tobin.recipe.ui.adapter.RecipeResultAdapter;;
import com.tobin.recipe.bean.RecipesBean;
import com.tobin.recipe.bean.RecipesClassBean.ResultBean.ListBean;

@Route(path = RouterHub.RECIPE_RESULT_FRAGMENT)
public class RecipeResultFragment extends BaseFragment {
    @Autowired
    public RecipesBean recipesBean;

    @Autowired
    public ListBean listBean;

    RecipeResultViewModel viewModel;

    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.colorPrimaryDark)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    protected void initData() {

        if (listBean != null) {
            viewModel.byRecipesClass(Integer.parseInt(listBean.getClassid()), 0, 20);
        } else {
            viewModel.byRecipesClass(5, 0, 20);
        }

    }

    @Override
    protected void initViewModel() {
        viewModel = getFragmentScopeViewModel(RecipeResultViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.recipe_fragment_result, BR.vm, viewModel)
                .addBindingParam(BR.click, new ClickProxy())
                .addBindingParam(BR.adapter, new RecipeResultAdapter(getContext()));
    }

    public class ClickProxy {

    }

}