package com.tobin.recipe.ui;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseActivity;
import com.tobin.recipe.databinding.ActivityRecipeBinding;
import com.tobin.recipe.R;

@Route(path = RouterHub.RECIPE_RECIPE_ACTIVITY)
public class RecipeActivity extends BaseActivity<RecipeViewModel, ActivityRecipeBinding> {

    @Override
    protected int onCreate() {
        return R.layout.activity_recipe;
    }

    @Override
    protected void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        RecipeFragment  mineFragment = new RecipeFragment();
        transaction.replace(R.id.fragment_container, mineFragment);
        transaction.commit();
    }

    @Override
    protected void initData() {

//        Box.getRetrofit(RecipeApi.class)
//                .recipesClass()
//                .compose(RxUtils.httpResponseTransformer(false))
//                .compose(provider.<RecipesClassBean>bindToLifecycle())
//                .subscribe(new CommonObserver<RecipesClassBean>() {
//                    @Override
//                    public void onNext(RecipesClassBean recipesClassBean) {
//
//                    }
//
//                    @Override
//                    protected void onError(ApiException ex) {
//                        super.onError(ex);
//
//                    }
//
//                    @Override
//                    protected void onNetError() {
//
//                    }
//                });

    }

    @Override
    protected RecipeViewModel initViewModel() {
        return new ViewModelProvider(this).get(RecipeViewModel.class);
    }

    @Override
    protected void showError(Object obj) {

    }
}