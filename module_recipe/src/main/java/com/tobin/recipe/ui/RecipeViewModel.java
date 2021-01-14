package com.tobin.recipe.ui;

import androidx.lifecycle.MutableLiveData;

import com.tobin.lib_core.base.Box;
import com.tobin.lib_core.http.exception.ApiException;
import com.tobin.lib_core.utils.RxUtils;
import com.tobin.lib_resource.lifecycle.BaseViewModel;
import com.tobin.recipe.api.RecipeApi;
import com.tobin.recipe.bean.RecipesBean;
import com.tobin.recipe.bean.RecipesClassBean;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class RecipeViewModel extends BaseViewModel {
    private final String TAG = "RecipeViewModel";

    private MutableLiveData<RecipesBean> recipesLiveData;
    private MutableLiveData<RecipesClassBean> recipesClassLiveData;


    public RecipeViewModel() {
        recipesLiveData = new MutableLiveData<>();
        recipesClassLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RecipesBean> getRecipesLiveData() {
        recipesClass();
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
                    Timber.tag(TAG).i(recipesClassBean.toString());
                    recipesClassLiveData.postValue(recipesClassBean);
                }, throwable ->{
                    Timber.tag(TAG).i("throwable message: %s", throwable.getMessage());
                    error.postValue(throwable);
                } , () -> {
                    Timber.tag(TAG).e("onComplete");
                    // 请求结束
                }));
    }

}