package com.tobin.recipe.ui.classify;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;

import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig;
import com.tobin.recipe.BR;
import com.tobin.recipe.R;
import com.tobin.recipe.bean.RecipesClassBean;

import timber.log.Timber;

@Route(path = RouterHub.RECIPE_RECIPE_FRAGMENT)
public class RecipeClassFragment extends BaseFragment {
    private RecipesClassBean recipesClassBean;
    private RecipeClassViewModel recipeClassViewModel;

    @Override
    protected void loadInitData() {
        super.loadInitData();
        recipeClassViewModel.getRecipesClassLiveData().observe(this, recipesClassBean -> {
            Timber.tag("Tobin").i("RecipeFragment initData");
            if (recipesClassBean != null) {
                this.recipesClassBean = recipesClassBean;

            }

        });
    }

    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.colorPrimaryDark)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void initViewModel() {
        recipeClassViewModel = getFragmentScopeViewModel(RecipeClassViewModel.class);
    }


    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.recipe_fragment_class, BR.vm, recipeClassViewModel)
                .addBindingParam(BR.click, new ClickProxy());

    }

    public class ClickProxy {

    }


}