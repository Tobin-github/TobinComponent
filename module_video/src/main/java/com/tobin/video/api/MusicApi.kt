package com.tobin.video.api

import com.tobin.lib_core.http.model.HttpResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by Tobin on 2021/4/20
 * Email: 616041023@qq.com
 * Description:
 */
interface MusicApi {
    /**
     * 热门音乐
     *
     * @return String
     */
    @Headers("Domain-Name:music163")
    @GET("/discover/toplist?id=19723756")
    fun getTopList(): Observable<HttpResult<Any>>
}