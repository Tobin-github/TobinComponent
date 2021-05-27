package com.tobin.video.ui

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.tobin.lib_resource.arouter.RouterHub
import com.tobin.lib_resource.mvvm.base.BaseFragment
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig
import com.tobin.video.BR
import com.tobin.video.R
import com.tobin.video.databinding.VideoFragmentBinding

/**
 * Created by Tobin on 2021/4/20
 * Email: 616041023@qq.com
 * Description:
 */

@Route(path = RouterHub.APP_VIDEO_FRAGMENT)
class VideoFragment : BaseFragment() {

    var mBinding = binding as VideoFragmentBinding;

    companion object {
        fun newInstance() = VideoFragment()
    }

    private lateinit var viewModel: VideoViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        mBinding.gsyVideo.setUp("http://ivi.bupt.edu.cn/hls/cctv3hd.m3u8",
//                true, "CCTV-3");

        mBinding.gsyVideo.setUp("http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8",
                true, "CCTV-6")
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTopList()
        mBinding.gsyVideo.onVideoResume()
    }

    override fun onPause() {
        super.onPause()
        mBinding.gsyVideo.onVideoPause()
    }

    override fun onDestroyView() {
        mBinding.gsyVideo.release()
        super.onDestroyView()
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