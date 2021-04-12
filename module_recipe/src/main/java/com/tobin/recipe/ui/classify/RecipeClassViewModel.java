package com.tobin.recipe.ui.classify;

import androidx.lifecycle.MutableLiveData;

import com.tobin.lib_core.base.Box;
import com.tobin.lib_core.utils.RxUtils;
import com.tobin.lib_resource.lifecycle.BaseViewModel;
import com.tobin.recipe.api.RecipeApi;
import com.tobin.recipe.bean.RecipesBean;
import com.tobin.recipe.bean.RecipesClassBean;

import timber.log.Timber;

public class RecipeClassViewModel extends BaseViewModel {

    private final MutableLiveData<RecipesBean> recipesLiveData;
    private final MutableLiveData<RecipesClassBean> recipesClassLiveData;


    public RecipeClassViewModel() {
        recipesLiveData = new MutableLiveData<>();
        recipesClassLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RecipesBean> getRecipesLiveData() {
        return recipesLiveData;
    }

    public MutableLiveData<RecipesClassBean> getRecipesClassLiveData() {
        recipesClass();
        return recipesClassLiveData;
    }

    public void recipesClass() {
        addDisposable(Box.getRetrofit(RecipeApi.class)
                .recipesClass()
                .compose(RxUtils.httpResponseTransformer())
                .subscribe(recipesClassBean -> {
                    Timber.tag("Tobin").i("RecipeViewModel --> %s", recipesClassBean.toString());
                    recipesClassLiveData.postValue(recipesClassBean);
                }, throwable ->{
                    Timber.tag("Tobin").i("RecipeViewModel throwable message: %s", throwable.getMessage());
                    error.postValue(throwable);
                } , () -> {
                    Timber.tag("Tobin").e("RecipeViewModel recipesClass onComplete");
                    // 请求结束
                }));
    }

}