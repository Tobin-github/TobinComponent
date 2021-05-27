package com.tobin.video.ui

import com.tobin.lib_core.base.Box
import com.tobin.lib_core.utils.RxUtils
import com.tobin.lib_resource.lifecycle.BaseViewModel
import com.tobin.video.api.MusicApi
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import timber.log.Timber
import java.util.*

class VideoViewModel : BaseViewModel() {

    fun getTopList() {
        addDisposable(Box.getRetrofit(MusicApi::class.java)
                .getTopList()
                .compose<Any>(RxUtils.httpResponseTransformer())
                .subscribe({ result: Any ->
                    Timber.tag("Tobin").i("VideoViewModel --> getMsg: %s", result.toString())
                }, { throwable: Throwable ->
                    Timber.tag("Tobin").i("VideoViewModel throwable message: %s", throwable.message)
                    error.postValue(throwable)
                }, { Timber.tag("Tobin").e("VideoViewModel topList onComplete") }))
    }
}