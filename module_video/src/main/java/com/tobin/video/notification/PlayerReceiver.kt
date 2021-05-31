package com.tobin.video.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.view.KeyEvent
import timber.log.Timber
import java.util.*

/**
 * Created by Tobin on 2021/5/31
 * Email: 616041023@qq.com
 * Description:
 */
class PlayerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action == Intent.ACTION_MEDIA_BUTTON) {
            if (intent.extras == null) {
                return
            }
            val keyEvent = intent.extras!![Intent.EXTRA_KEY_EVENT] as KeyEvent? ?: return
            if (keyEvent.action != KeyEvent.ACTION_DOWN) {
                return
            }
            when (keyEvent.keyCode) {
                KeyEvent.KEYCODE_HEADSETHOOK,
                KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
                    Timber.i("PlayerReceiver togglePlay")
                }
                KeyEvent.KEYCODE_MEDIA_PLAY -> {
                    Timber.i("PlayerReceiver playAudio")
                }
                KeyEvent.KEYCODE_MEDIA_PAUSE -> {
                    Timber.i("PlayerReceiver pauseAudio")
                }
                KeyEvent.KEYCODE_MEDIA_STOP -> {
                    Timber.i("PlayerReceiver KEYCODE_MEDIA_STOP")
                }
                KeyEvent.KEYCODE_MEDIA_NEXT -> {
                    Timber.i("PlayerReceiver playNext")
                }
                KeyEvent.KEYCODE_MEDIA_PREVIOUS -> {
                    Timber.i("PlayerReceiver playPrevious")
                }
                else -> {
                }
            }
        } else {
            if (Objects.requireNonNull(intent.action) == PlayerService.NOTIFY_PLAY) {
                Timber.i("PlayerReceiver playAudio")
            } else if (intent.action == PlayerService.NOTIFY_PAUSE || intent.action == AudioManager.ACTION_AUDIO_BECOMING_NOISY) {
                Timber.i("PlayerReceiver pauseAudio")
            } else if (intent.action == PlayerService.NOTIFY_NEXT) {
                Timber.i("PlayerReceiver playNext")
            } else if (intent.action == PlayerService.NOTIFY_CLOSE) {
                Timber.i("PlayerReceiver close")
            } else if (intent.action == PlayerService.NOTIFY_PREVIOUS) {
                Timber.i("PlayerReceiver playPrevious")
            } else if (intent.action == PlayerService.NOTIFY_ACTION_CONTENT) {
                Timber.i("PlayerReceiver NOTIFY_ACTION_CONTENT")

            }
        }
    }
}