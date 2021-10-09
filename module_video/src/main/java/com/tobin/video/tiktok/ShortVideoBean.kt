package com.tobin.video.tiktok

import java.io.Serializable

/**
 * 短视频信息表
 */
class ShortVideoBean : Serializable {
    companion object {
        const val serialVersionUID = 1L
    }

    // 视频播放Url
    var videoUrl: String? = null

    // 文本内容
    var textContent: String? = null

    // 视频封面
    var videoCover: String? = null

}