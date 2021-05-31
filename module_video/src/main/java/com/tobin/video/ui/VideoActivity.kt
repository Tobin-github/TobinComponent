package com.tobin.video.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.tobin.lib_resource.arouter.RouterHub
import com.tobin.lib_resource.mvvm.base.BaseActivity
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig
import com.tobin.video.BR
import com.tobin.video.R

@Route(path = RouterHub.APP_VIDEO_ACTIVITY)
class VideoActivity : BaseActivity() {
    private lateinit var viewModel: VideoShareViewModel

    override fun initViewModel() {
        viewModel = getActivityScopeViewModel(VideoShareViewModel::class.java) as VideoShareViewModel
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.video_activity, BR.vm, viewModel)
            .addBindingParam(BR.click, ClickProxy())
    }

    inner class ClickProxy {

    }
}