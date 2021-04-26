package com.tobin.video.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.tobin.lib_resource.arouter.RouterHub
import com.tobin.lib_resource.mvvm.base.BaseFragment
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig
import com.tobin.video.BR
import com.tobin.video.R

@Route(path = RouterHub.APP_VIDEO_FRAGMENT)
class VideoFragment : BaseFragment() {

    companion object {
        fun newInstance() = VideoFragment()
    }

    private lateinit var viewModel: VideoViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        viewModel.getTopList()
    }

    override fun initViewModel() {
        viewModel = getFragmentScopeViewModel(VideoViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.video_fragment, BR.vm, viewModel)
                .addBindingParam(BR.click, ClickProxy())
    }

    inner class ClickProxy {

    }
}