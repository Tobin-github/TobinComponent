package com.tobin.video.tiktok

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.tobin.lib_resource.arouter.RouterHub
import com.tobin.lib_resource.mvvm.base.BaseActivity
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig
import com.tobin.video.BR
import com.tobin.video.R
import com.tobin.video.databinding.VideoTiktokActivityBinding
import timber.log.Timber
import java.util.ArrayList

@Route(path = RouterHub.APP_VIDEO_TIKTOK_ACTIVITY)
class TikTokVideoActivity : BaseActivity() {
    private lateinit var viewModel: TikTokViewModel
    private lateinit var binding: VideoTiktokActivityBinding
    private var mList: MutableList<ShortVideoBean> = ArrayList()
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getBinding() as VideoTiktokActivityBinding
        getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    private fun getData() {
        setData()
        viewPagerAdapter = ViewPagerAdapter(this, mList)
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.viewPager2.adapter = viewPagerAdapter
        binding.viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // 大于0说明有播放
                val playPosition = GSYVideoManager.instance().playPosition
                if (playPosition >= 0) {
                    // 对应的播放列表TAG
                    if (GSYVideoManager.instance().playTag == RecyclerItemNormalHolder.TAG && position != playPosition) {
                        playPosition(position)
                    }
                }
            }
        })
//        binding.viewPager2.post { playPosition(0) }
    }

    private fun playPosition(position: Int) {
        val viewHolder =
            (binding.viewPager2.getChildAt(0) as RecyclerView).findViewHolderForAdapterPosition(position)
        if (viewHolder != null) {
            val recyclerItemNormalHolder = viewHolder as RecyclerItemNormalHolder
            recyclerItemNormalHolder.play()
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

    /**
     * 模拟数据
     */
    private fun setData() {
        val data1 = ShortVideoBean()
        data1.textContent = "蜂鸟计划 中国预告片：速度与金钱版 (中文字幕)"
        data1.videoCover = "https://img3.doubanio.com/img/trailer/medium/2631410731.jpg?1611566097"
        data1.videoUrl =
            "https://vt1.doubanio.com/202102020903/722442386dcd5076fd70c4ac2bf093bb/view/movie/M/402710160.mp4"
        mList.add(data1)
        val data2 = ShortVideoBean()
        data2.textContent = "旺达幻视 预告片"
        data2.videoCover = "https://img1.doubanio.com/img/trailer/medium/2628042057.jpg?"
        data2.videoUrl =
            "https://vt1.doubanio.com/202102011621/94e560ba4d88c562e0768f6339822d99/view/movie/M/402690624.mp4"
        mList.add(data2)
        val data3 = ShortVideoBean()
        data3.textContent = "无耻之徒(美版) 第十一季 预告片"
        data3.videoCover = "https://img1.doubanio.com/img/trailer/medium/2626877508.jpg?"
        data3.videoUrl =
            "https://vt1.doubanio.com/202101120940/a3e7ae32c21341710eaceba2d2e56039/view/movie/M/402680931.mp4"
        mList.add(data3)
        val data4 = ShortVideoBean()
        data4.textContent = "发现女巫 第二季 预告片"
        data4.videoCover = "https://img9.doubanio.com/img/trailer/medium/2628112124.jpg?"
        data4.videoUrl =
            "https://vt1.doubanio.com/202101120938/d05ce0af6cefa6b88dd699e1f8150f2f/view/movie/M/402690672.mp4"
        mList.add(data4)
        val data5 = ShortVideoBean()
        data5.textContent = "天国与地狱 预告片"
        data5.videoCover = "https://img2.doubanio.com/img/trailer/medium/2628313153.jpg?"
        data5.videoUrl =
            "https://vt1.doubanio.com/202102051113/07846ae6e7dd67089ff46a4d070b5f5d/view/movie/M/402690752.mp4"
        mList.add(data5)
        Timber.d("list: %s", mList.toString())
    }
}