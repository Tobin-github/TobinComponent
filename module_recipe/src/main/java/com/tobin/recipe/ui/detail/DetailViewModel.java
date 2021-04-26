package com.tobin.recipe.ui.detail;

import androidx.lifecycle.MutableLiveData;

import com.tobin.lib_core.base.Box;
import com.tobin.lib_core.utils.RxUtils;
import com.tobin.lib_resource.lifecycle.BaseViewModel;
import com.tobin.recipe.api.RecipeApi;
import com.tobin.recipe.bean.RecipesBean;

import timber.log.Timber;

public class DetailViewModel extends BaseViewModel {
    public MutableLiveData<RecipesBean> recipesDetailLiveData;

    public DetailViewModel() {
        recipesDetailLiveData = new MutableLiveData<>();
    }

    public void recipesDetail(int classId) {
        addDisposable(Box.getRetrofit(RecipeApi.class)
                .recipesDetail(classId)
                .compose(RxUtils.httpResponseTransformer())
                .subscribe(recipesBean -> {
                    Timber.tag("Tobin").i("DetailViewModel --> getMsg: %s", recipesBean.getMsg());
                    recipesDetailLiveData.postValue(recipesBean);
                }, throwable ->{
                    Timber.tag("Tobin").i("DetailViewModel throwable message: %s", throwable.getMessage());
                    error.postValue(throwable);
                } , () -> {
                    Timber.tag("Tobin").e("DetailViewModel byRecipesClass onComplete");
                    // 请求结束
                }));
    }
}