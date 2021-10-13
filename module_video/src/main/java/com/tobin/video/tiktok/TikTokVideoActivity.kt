package com.tobin.video.tiktok

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.tobin.lib_resource.arouter.RouterHub
import com.tobin.lib_resource.mvvm.base.BaseActivity
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig
import com.tobin.video.BR
import com.tobin.video.R

@Route(path = RouterHub.APP_VIDEO_TIKTOK_ACTIVITY)
class TikTokVideoActivity : BaseActivity() {
    private lateinit var viewModel: TikTokViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TikTokFragment.newInstance())
                .commitNow()
        }
    }

    override fun initViewModel() {
        viewModel = getActivityScopeViewModel(TikTokViewModel::class.java) as TikTokViewModel
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.video_tiktok_activity, BR.vm, viewModel)
            .addBindingParam(BR.click, ClickProxy())
    }

    inner class ClickProxy {

    }


}