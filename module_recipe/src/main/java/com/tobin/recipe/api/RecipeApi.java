package com.tobin.recipe.api;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;

import com.tobin.lib_core.http.model.HttpResult;
import com.tobin.recipe.bean.RecipesBean;
import com.tobin.recipe.bean.RecipesClassBean;

/**
 * Created by Tobin on 2021/1/12
 * Email: 616041023@qq.com
 * Description:
 */
public interface RecipeApi {

    String appkey = "2a5f3669118e8a082a1697c6b6f73f9a";

    // 菜谱查询
//    @Headers({"Domain-Name:wxjdcloud"})
//    @GET("/jisuapi/search")
//    Observable<HttpResult<RecipesBean>> recipesSearch(@QueryMap Map<String, Object> body);

    @Headers({"Domain-Name:wxjdcloud"})
    @GET("/jisuapi/search?appkey=" + appkey)
    Observable<HttpResult<RecipesBean>> recipesSearch(@Query("keyword") String keyword, @Query("num") String number);

    // 菜谱分类
    @Headers({"Domain-Name:wxjdcloud"})
    @GET("/jisuapi/recipe_class?appkey=" + appkey)
    Observable<HttpResult<RecipesClassBean>> recipesClass();

    // 按分类检索
    @Headers({"Domain-Name:wxjdcloud"})
    @GET("/jisuapi/byclass")
    Flowable<HttpResult<RecipesBean>> byRecipesClass(@QueryMap Map<String, Object> body);

    // 根据ID查询详情
    @Headers({"Domain-Name:wxjdcloud"})
    @GET("/jisuapi/detail")
    Flowable<HttpResult<RecipesClassBean>> recipesDetail(@QueryMap Map<String, Object> body);

    @GET
    Flowable<String> test(@QueryMap Map<String, Object> body);
}
