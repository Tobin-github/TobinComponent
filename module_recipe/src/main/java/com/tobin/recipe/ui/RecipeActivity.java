package com.tobin.recipe.ui;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tobin.lib_core.base.Box;
import com.tobin.lib_core.http.exception.ApiException;
import com.tobin.lib_core.http.observer.CommonObserver;
import com.tobin.lib_core.utils.RxUtils;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.base.BaseDBActivity;
import com.tobin.recipe.api.RecipeApi;
import com.tobin.recipe.bean.RecipesClassBean;
import com.tobin.recipe.databinding.ActivityRecipeBinding;
import com.tobin.recipe.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

@Route(path = RouterHub.RECIPE_RECIPE_ACTIVITY)
public class RecipeActivity extends BaseDBActivity<ActivityRecipeBinding> {

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
}