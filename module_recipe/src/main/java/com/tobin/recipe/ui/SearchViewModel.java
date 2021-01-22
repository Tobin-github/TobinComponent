package com.tobin.recipe.ui;

import androidx.lifecycle.MutableLiveData;

import com.tobin.lib_core.base.Box;
import com.tobin.lib_core.utils.RxUtils;
import com.tobin.lib_resource.lifecycle.BaseViewModel;
import com.tobin.recipe.api.RecipeApi;
import com.tobin.recipe.bean.RecipesBean;

import timber.log.Timber;

/**
 * Created by Tobin on 2021/1/20
 * Email: 616041023@qq.com
 * Description:
 */
public class SearchViewModel extends BaseViewModel {
    private MutableLiveData<RecipesBean> recipesSearchLiveData;

    public SearchViewModel() {
        recipesSearchLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RecipesBean> getRecipesSearchLiveData() {
        return recipesSearchLiveData;
    }

    public void recipesSearch(String keyword) {
        addDisposable(Box.getRetrofit(RecipeApi.class)
                .recipesSearch(keyword)
                .compose(RxUtils.httpResponseTransformer())
                .subscribe(recipesBean -> {
                    Timber.tag("Tobin").i("RecipeResultViewModel --> %s", recipesBean.toString());
                    recipesSearchLiveData.postValue(recipesBean);
                }, throwable -> {
                    Timber.tag("Tobin").i("RecipeResultViewModel throwable message: %s", throwable.getMessage());
                    error.postValue(throwable);
                }, () -> {
                    Timber.tag("Tobin").e("RecipeResultViewModel recipesSearch onComplete");
                    // 请求结束
                }));
    }
}
