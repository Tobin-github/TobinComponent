package com.tobin.mine.api

import com.tobin.lib_core.http.model.HttpResult
import com.tobin.mine.bean.WanUserBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Tobin on 2021/10/11.
 * Email: 616041023@qq.com
 * Description: WanUserApi.
 */
interface WanUserApi {
    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user/register")
    fun register(
        @Field("username") usermame: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): Observable<HttpResult<WanUserBean>>

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("username") usermame: String,
        @Field("password") password: String,
    ): Observable<HttpResult<WanUserBean>>

    /**
     * 退出
     */
    @FormUrlEncoded
    @POST("user/logout/json")
    fun logout(): Observable<HttpResult<Any>>
}