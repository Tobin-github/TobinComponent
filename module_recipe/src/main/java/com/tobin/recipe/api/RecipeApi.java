package com.tobin.recipe.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

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
//    String apiUrl = "https://way.jd.com/";

    /**
     * 菜谱搜索.
     *
     * @param keyword 关键字
     * @return RecipesBean
     */
    @Headers({"Domain-Name:wxjdcloud"})
    @GET("/jisuapi/search?appkey=" + appkey)
    Observable<HttpResult<RecipesBean>> recipesSearch(@Query("keyword") String keyword);

    /**
     * 菜谱分类
     *
     * @return RecipesClassBean
     */
    @Headers({"Domain-Name:wxjdcloud"})
    @GET("/jisuapi/recipe_class?appkey=" + appkey)
    Observable<HttpResult<RecipesClassBean>> recipesClass();

    /**
     * 按分类检索.
     *
     * @param classid 分类ID
     * @param start   起始条数
     * @param num     获取数量，最大为20
     * @return RecipesBean
     */
    @Headers({"Domain-Name:wxjdcloud"})
    @GET("/jisuapi/byclass?appkey=" + appkey)
    Observable<HttpResult<RecipesBean>> byRecipesClass(@Query("classid") int classid,
                                                       @Query("start") int start,
                                                       @Query("num") int num);

    /**
     * 根据菜谱ID查询详情.
     *
     * @param id 菜谱ID
     * @return RecipesBean
     */
    @Headers({"Domain-Name:wxjdcloud"})
    @GET("/jisuapi/detail")
    Observable<HttpResult<RecipesBean>> recipesDetail(@Query("id") int id);

}
