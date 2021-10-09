package com.tobin.video.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.cache.CacheFactory
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.tobin.lib_core.base.delegate.AppLifecycle
import com.tobin.lib_resource.BuildConfig
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import timber.log.Timber
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager
import tv.danmaku.ijk.media.exo2.ExoPlayerCacheManager

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

        GSYVideoManager.instance().enableRawPlay(application);
        PlayerFactory.setPlayManager(Exo2PlayerManager().javaClass)
        CacheFactory.setCacheManager(ExoPlayerCacheManager().javaClass)
        GSYVideoType.enableMediaCodec()
        Debuger.enable()
        //application.registerActivityLifecycleCallbacks(this)
    }

    override fun onTerminate(application: Application) {
        Timber.tag("Tobin").i("AppVideo onTerminate")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Timber.tag("Tobin").i("AppVideo onConfigurationChanged")
    }
}