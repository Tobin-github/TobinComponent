package com.tobin.video.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.tobin.lib_core.base.delegate.AppLifecycle
import com.tobin.lib_resource.BuildConfig
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import timber.log.Timber

/**
 * Created by Tobin on 2021/4/20
 * Email: 616041023@qq.com
 * Description:
 */
class AppVideo : AppLifecycle{
    override fun attachBaseContext(base: Context) {
        Timber.tag("Tobin").i("AppVideo attachBaseContext")
    }

    override fun onCreate(application: Application) {
        Timber.tag("Tobin").i("AppVideo onCreate")
    }

    override fun onTerminate(application: Application) {
        Timber.tag("Tobin").i("AppVideo onTerminate")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Timber.tag("Tobin").i("AppVideo onConfigurationChanged")
    }
}