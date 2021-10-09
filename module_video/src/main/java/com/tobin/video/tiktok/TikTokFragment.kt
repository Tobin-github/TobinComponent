package com.tobin.video.tiktok

import com.alibaba.android.arouter.facade.annotation.Route
import com.tobin.lib_resource.arouter.RouterHub
import com.tobin.lib_resource.mvvm.base.BaseFragment
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig
import com.tobin.video.BR
import com.tobin.video.R

@Route(path = RouterHub.APP_VIDEO_TIKTOK_FRAGMENT)
class TikTokFragment : BaseFragment() {

    companion object {
        fun newInstance() = TikTokFragment()
    }

    private lateinit var viewModel: TikTokViewModel

    override fun initViewModel() {
        viewModel = getFragmentScopeViewModel(TikTokViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.video_tiktok_fragment, BR.vm, viewModel)
    }

}