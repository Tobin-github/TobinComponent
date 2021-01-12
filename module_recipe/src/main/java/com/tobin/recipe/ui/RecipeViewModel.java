package com.tobin.recipe.ui;

import com.tobin.lib_core.base.Box;
import com.tobin.lib_core.http.exception.ApiException;
import com.tobin.lib_core.http.observer.CommonObserver;
import com.tobin.lib_core.utils.RxUtils;
import com.tobin.lib_resource.lifecycle.BaseViewModel;
import com.tobin.recipe.api.RecipeApi;
import com.tobin.recipe.bean.RecipesClassBean;

import java.util.HashMap;
import java.util.Map;

public class RecipeViewModel extends BaseViewModel {

    private void recipesClass(){
        Map<String, Object> bodyMaps = new HashMap<>();
        bodyMaps.put("appkey", RecipeApi.appKey);

        Box.getRetrofit(RecipeApi.class)
                .recipesClass(bodyMaps)
                .compose(RxUtils.httpResponseTransformer(false))
                .subscribe(new CommonObserver<RecipesClassBean>() {
                    @Override
                    public void onNext(RecipesClassBean recipesClassBean) {

                    }

                    @Override
                    protected void onError(ApiException ex) {
                        super.onError(ex);

                    }

                    @Override
                    protected void onNetError() {

                    }
                });


    }
}