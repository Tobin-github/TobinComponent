package com.tobin.recipe.api;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import com.tobin.lib_core.http.model.HttpResult;
import com.tobin.recipe.bean.RecipesBean;
import com.tobin.recipe.bean.RecipesClassBean;

/**
 * Created by Tobin on 2021/1/12
 * Email: 616041023@qq.com
 * Description:
 */
public interface RecipeApi {

    String BASE_URL_DEVELOP = "https://way.jd.com";  // 开发环境
    String appKey = "2a5f3669118e8a082a1697c6b6f73f9a";
    
    // 菜谱查询
    @GET("/jisuapi/search")
    Flowable<HttpResult<RecipesBean>> recipesSearch(@QueryMap Map<String, Object> body);

    // 菜谱分类
    @GET("/jisuapi/recipe_class")
    Observable<HttpResult<RecipesClassBean>> recipesClass(@QueryMap Map<String, Object> body);

    // 按分类检索
    @GET("/jisuapi/byclass")
    Flowable<HttpResult<RecipesBean>> byRecipesClass(@QueryMap Map<String, Object> body);

    // 根据ID查询详情
    @GET("/jisuapi/detail")
    Flowable<HttpResult<RecipesClassBean>> recipesDetail(@QueryMap Map<String, Object> body);
}
