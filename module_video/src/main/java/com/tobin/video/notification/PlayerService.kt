package com.tobin.video.notification

import android.app.*
import android.content.Intent
import android.os.IBinder

/**
 * Created by Tobin on 2021/5/24
 * Email: 616041023@qq.com
 * Description:
 */
class PlayerService : Service() {
    val NOTIFY_PREVIOUS = "com.tobin.audio.previous"
    val NOTIFY_CLOSE = "com.tobin.audio.close"
    val NOTIFY_PAUSE = "com.tobin.audio.pause"
    val NOTIFY_PLAY = "com.tobin.audio.play"
    val NOTIFY_NEXT = "com.tobin.audio.next"
    private val GROUP_ID = "group_001"
    private val CHANNEL_ID = "channel_001"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotification() {

    }


    private fun requestAlbumCover(coverUrl: String, musicId: String) {

    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}