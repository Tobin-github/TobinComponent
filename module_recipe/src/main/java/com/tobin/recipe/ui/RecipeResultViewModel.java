package com.tobin.recipe.ui;

import androidx.lifecycle.MutableLiveData;

import com.tobin.lib_core.base.Box;
import com.tobin.lib_core.utils.RxUtils;
import com.tobin.lib_resource.lifecycle.BaseViewModel;
import com.tobin.recipe.api.RecipeApi;
import com.tobin.recipe.bean.RecipesBean;

import timber.log.Timber;

public class RecipeResultViewModel extends BaseViewModel {
    private MutableLiveData<RecipesBean> byClassIdLiveData;
    private MutableLiveData<RecipesBean> recipesSearchLiveData;

    public RecipeResultViewModel() {
        byClassIdLiveData = new MutableLiveData<>();
        recipesSearchLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RecipesBean> getRecipesLiveData() {
        return byClassIdLiveData;
    }

    public MutableLiveData<RecipesBean> getRecipesSearchLiveData() {
        return recipesSearchLiveData;
    }

    public void byRecipesClass(int classId, int start, int num) {
        addDisposable(Box.getRetrofit(RecipeApi.class)
                .byRecipesClass(classId, start, num)
                .compose(RxUtils.httpResponseTransformer())
                .subscribe(recipesBean -> {
                    Timber.tag("Tobin").i("RecipeResultViewModel --> %s", recipesBean.toString());
                    byClassIdLiveData.postValue(recipesBean);
                }, throwable ->{
                    Timber.tag("Tobin").i("RecipeResultViewModel throwable message: %s", throwable.getMessage());
                    error.postValue(throwable);
                } , () -> {
                    Timber.tag("Tobin").e("RecipeResultViewModel byRecipesClass onComplete");
                    // 请求结束
                }));
    }

    public void recipesSearch(String keyword) {
        addDisposable(Box.getRetrofit(RecipeApi.class)
                .recipesSearch(keyword)
                .compose(RxUtils.httpResponseTransformer())
                .subscribe(recipesBean -> {
                    Timber.tag("Tobin").i("RecipeResultViewModel --> %s", recipesBean.toString());
                    recipesSearchLiveData.postValue(recipesBean);
                }, throwable ->{
                    Timber.tag("Tobin").i("RecipeResultViewModel throwable message: %s", throwable.getMessage());
                    error.postValue(throwable);
                } , () -> {
                    Timber.tag("Tobin").e("RecipeResultViewModel recipesSearch onComplete");
                    // 请求结束
                }));
    }
}