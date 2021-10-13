package com.tobin.video.tiktok

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.*
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.tobin.video.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer
import com.shuyu.gsyvideoplayer.utils.CommonUtil
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import android.widget.ImageView
import com.shuyu.gsyvideoplayer.utils.Debuger
import android.widget.SeekBar

class TikTokCoverVideo : StandardGSYVideoPlayer {
    private lateinit var mCoverImage: ImageView
    private var mCoverOriginUrl: String? = null
    private var mCoverOriginId = 0
    private var mDefaultRes = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun init(context: Context) {
        super.init(context)
        mCoverImage = findViewById(R.id.thumbImage)
        if (mThumbImageViewLayout != null &&
            (mCurrentState == -1 || mCurrentState == CURRENT_STATE_NORMAL
                    || mCurrentState == CURRENT_STATE_ERROR)
        ) {
            mThumbImageViewLayout.visibility = VISIBLE
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.video_tiktok_cover
    }

    private fun loadCoverImage(url: String?, res: Int) {
        mCoverOriginUrl = url
        mDefaultRes = res
        Glide.with(context.applicationContext)
            .setDefaultRequestOptions(
                RequestOptions()
                    .frame(1000000)
                    .centerCrop()
                    .error(res)
                    .placeholder(res)
            )
            .load(url)
            .into(mCoverImage)
    }

    private fun loadCoverImageBy(id: Int, res: Int) {
        mCoverOriginId = id
        mDefaultRes = res
        mCoverImage.setImageResource(id)
    }

    override fun startWindowFullscreen(
        context: Context,
        actionBar: Boolean,
        statusBar: Boolean
    ): GSYBaseVideoPlayer {
        val gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar)
        val sampleCoverVideo = gsyBaseVideoPlayer as TikTokCoverVideo
        if (mCoverOriginUrl != null) {
            sampleCoverVideo.loadCoverImage(mCoverOriginUrl, mDefaultRes)
        } else if (mCoverOriginId != 0) {
            sampleCoverVideo.loadCoverImageBy(mCoverOriginId, mDefaultRes)
        }
        return gsyBaseVideoPlayer
    }

    override fun showSmallVideo(
        size: Point,
        actionBar: Boolean,
        statusBar: Boolean
    ): GSYBaseVideoPlayer {
        //下面这里替换成你自己的强制转化
        val sampleCoverVideo = super.showSmallVideo(size, actionBar, statusBar) as TikTokCoverVideo
        sampleCoverVideo.mStartButton.visibility = GONE
        sampleCoverVideo.mStartButton = null
        return sampleCoverVideo
    }

    override fun cloneParams(from: GSYBaseVideoPlayer, to: GSYBaseVideoPlayer) {
        super.cloneParams(from, to)
        val sf = from as TikTokCoverVideo
        val st = to as TikTokCoverVideo
        st.mShowFullAnimation = sf.mShowFullAnimation
    }

    override fun isShowNetConfirm(): Boolean {
        return false
    }

    /**
     * 退出window层播放全屏效果
     */
    override fun clearFullscreenLayout() {
        if (!mFullAnimEnd) {
            return
        }
        mIfCurrentIsFullscreen = false
        var delay = 0
        if (mOrientationUtils != null) {
            delay = mOrientationUtils.backToProtVideo()
            mOrientationUtils.isEnable = false
            if (mOrientationUtils != null) {
                mOrientationUtils.releaseListener()
                mOrientationUtils = null
            }
        }
        if (!mShowFullAnimation) {
            delay = 0
        }
        val vp =
            CommonUtil.scanForActivity(context).findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val oldF = vp.findViewById<View>(fullId)
        if (oldF != null) {
            //此处fix bug#265，推出全屏的时候，虚拟按键问题
            val gsyVideoPlayer = oldF as TikTokCoverVideo
            gsyVideoPlayer.mIfCurrentIsFullscreen = false
        }
        if (delay == 0) {
            backToNormal()
        } else {
            postDelayed({ backToNormal() }, delay.toLong())
        }
    }

    /******************* 下方两个重载方法，在播放开始前不屏蔽封面，不需要可屏蔽  */
    override fun onSurfaceUpdated(surface: Surface) {
        super.onSurfaceUpdated(surface)
        if (mThumbImageViewLayout != null && mThumbImageViewLayout.visibility == VISIBLE) {
            mThumbImageViewLayout.visibility = INVISIBLE
        }
    }

    override fun setViewShowState(view: View, visibility: Int) {
        if (view === mThumbImageViewLayout && visibility != VISIBLE) {
            return
        }
        super.setViewShowState(view, visibility)
    }

    override fun onSurfaceAvailable(surface: Surface) {
        super.onSurfaceAvailable(surface)
        if (GSYVideoType.getRenderType() != GSYVideoType.TEXTURE) {
            if (mThumbImageViewLayout != null && mThumbImageViewLayout.visibility == VISIBLE) {
                mThumbImageViewLayout.visibility = INVISIBLE
            }
        }
    }

    /******************* 下方重载方法，在播放开始不显示底部进度和按键，不需要可屏蔽  */
    var byStartedClick = false
    override fun onClickUiToggle(e: MotionEvent) {
        if (mIfCurrentIsFullscreen && mLockCurScreen && mNeedLockFull) {
            setViewShowState(mLockScreen, VISIBLE)
            return
        }
        byStartedClick = true
        super.onClickUiToggle(e)
    }

    override fun changeUiToNormal() {
        super.changeUiToNormal()
        byStartedClick = false
    }

    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
        Debuger.printfLog("Sample changeUiToPreparingShow")
        setViewShowState(mBottomContainer, INVISIBLE)
        setViewShowState(mStartButton, INVISIBLE)
    }

    override fun changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow()
        Debuger.printfLog("Sample changeUiToPlayingBufferingShow")
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, INVISIBLE)
            setViewShowState(mStartButton, INVISIBLE)
        }
    }

    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
        Debuger.printfLog("Sample changeUiToPlayingShow")
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, INVISIBLE)
            setViewShowState(mStartButton, INVISIBLE)
        }
    }

    override fun startAfterPrepared() {
        super.startAfterPrepared()
        Debuger.printfLog("Sample startAfterPrepared")
        setViewShowState(mBottomContainer, INVISIBLE)
        setViewShowState(mStartButton, INVISIBLE)
        setViewShowState(mBottomProgressBar, VISIBLE)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        byStartedClick = true
        super.onStartTrackingTouch(seekBar)
    }

    override fun onSurfaceDestroyed(surface: Surface?): Boolean {
        return super.onSurfaceDestroyed(surface)
    }
}