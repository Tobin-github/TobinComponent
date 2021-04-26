package com.tobin.recipe.ui.result;

import androidx.lifecycle.MutableLiveData;

import com.tobin.lib_core.base.Box;
import com.tobin.lib_core.utils.RxUtils;
import com.tobin.lib_resource.lifecycle.BaseViewModel;
import com.tobin.recipe.api.RecipeApi;
import com.tobin.recipe.bean.RecipesBean;

import timber.log.Timber;

public class RecipeResultViewModel extends BaseViewModel {
    public MutableLiveData<RecipesBean> byClassIdLiveData;

    public RecipeResultViewModel() {
        byClassIdLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RecipesBean> getRecipesLiveData() {
        return byClassIdLiveData;
    }


    public void byRecipesClass(int classId, int start, int num) {
        addDisposable(Box.getRetrofit(RecipeApi.class)
                .byRecipesClass(classId, start, num)
                .compose(RxUtils.httpResponseTransformer())
                .subscribe(recipesBean -> {
                    Timber.tag("Tobin").i("RecipeResultViewModel --> getMsg: %s", recipesBean.getMsg());
                    byClassIdLiveData.postValue(recipesBean);
                }, throwable ->{
                    Timber.tag("Tobin").i("RecipeResultViewModel throwable message: %s", throwable.getMessage());
                    error.postValue(throwable);
                } , () -> {
                    Timber.tag("Tobin").e("RecipeResultViewModel byRecipesClass onComplete");
                    // 请求结束
                }));
    }


}