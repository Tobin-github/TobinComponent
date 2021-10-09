package com.tobin.video.tiktok

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.bumptech.glide.Glide
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.tobin.video.databinding.VideoTiktokPagerItemBinding

open class RecyclerItemNormalHolder(
    context: Context?,
    binding: VideoTiktokPagerItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    protected var context: Context? = null
    private var imageView: ImageView
    var binding: VideoTiktokPagerItemBinding
    private var gsyVideoOptionBuilder: GSYVideoOptionBuilder
    var recyclerBaseAdapter: RecyclerView.Adapter<*>? = null

    companion object {
        const val TAG = "RecyclerItemNormalHolder"
    }

    init {
        this.context = context
        this.binding = binding
        imageView = ImageView(context)
        gsyVideoOptionBuilder = GSYVideoOptionBuilder()
    }

    fun play() {
        binding.videoItemPlayer.startPlayLogic()
    }

    fun onBind(position: Int, videoModel: ShortVideoBean) {
        val videoUrl = videoModel.videoUrl
        val textContent = videoModel.textContent
        Glide.with(context!!).load(videoModel.videoCover).into(imageView)
        binding.tvContent.text = "$textContent"

        // 防止错位，离开释放
        binding.videoItemPlayer.initUIState()
        gsyVideoOptionBuilder
            .setIsTouchWiget(false)
            .setThumbImageView(imageView)
            .setUrl(videoUrl)
            .setVideoTitle(textContent)
            .setCacheWithPlay(true)
            .setRotateViewAuto(true)
            .setLockLand(true)
            .setPlayTag(TAG)
            .setShowFullAnimation(true)
            .setNeedLockFull(true)
            .setPlayPosition(position)
            .setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String, vararg objects: Any) {
                    super.onPrepared(url, *objects)
                    GSYVideoManager.instance().isNeedMute = false
                }

                override fun onQuitFullscreen(url: String, vararg objects: Any) {
                    super.onQuitFullscreen(url, *objects)
                    GSYVideoManager.instance().isNeedMute = false
                }

                override fun onEnterFullscreen(url: String, vararg objects: Any) {
                    super.onEnterFullscreen(url, *objects)
                    GSYVideoManager.instance().isNeedMute = false
                    binding.videoItemPlayer.currentPlayer.titleTextView.text = objects[0] as String
                }
            }).build(binding.videoItemPlayer)


        //增加title
        binding.videoItemPlayer.titleTextView.visibility = View.GONE

        //设置返回键
        binding.videoItemPlayer.backButton.visibility = View.GONE

        //设置全屏按键功能
        binding.videoItemPlayer.fullscreenButton.setOnClickListener {
            resolveFullBtn(binding.videoItemPlayer)
        }
    }

    /**
     * 全屏幕按键处理
     */
    private fun resolveFullBtn(standardGSYVideoPlayer: StandardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, true, true)
    }

}