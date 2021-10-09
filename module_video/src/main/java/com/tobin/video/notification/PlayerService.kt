package com.tobin.video.notification

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.tobin.video.R
import timber.log.Timber

/**
 * Created by Tobin on 2021/5/24
 * Email: 616041023@qq.com
 * Description:
 */
class PlayerService : Service() {

    companion object {
        const val NOTIFY_PREVIOUS = "com.tobin.audio.previous"
        const val NOTIFY_CLOSE = "com.tobin.audio.close"
        const val NOTIFY_PAUSE = "com.tobin.audio.pause"
        const val NOTIFY_PLAY = "com.tobin.audio.play"
        const val NOTIFY_NEXT = "com.tobin.audio.next"
        const val NOTIFY_ACTION_CONTENT = "com.tobin.audio.action.show.play"
        private const val GROUP_ID = "group_001"
        private const val CHANNEL_ID = "channel_001"
        private const val CHANNEL_NAME = "音乐播放"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotification()
        return START_NOT_STICKY
    }

    private fun createNotification() {
        try {
            val title = "音乐标题"
            val summary = "this album's summary"
            val simpleContentView = RemoteViews(
                applicationContext.packageName, R.layout.video_notify_player_small
            )
            val bigContentView = RemoteViews(
                applicationContext.packageName, R.layout.video_notify_player_big
            )
//            val intent = Intent(applicationContext, PlayerReceiver::class.java)
//            intent.action = "showPlayer"

            val contentIntent = PendingIntent.getBroadcast(
                applicationContext,
                0, Intent(NOTIFY_ACTION_CONTENT).setPackage(packageName),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            //val contentIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                val playGroup = NotificationChannelGroup(GROUP_ID, CHANNEL_NAME)
                notificationManager.createNotificationChannelGroup(playGroup)
                val playChannel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                playChannel.group = GROUP_ID
                notificationManager.createNotificationChannel(playChannel)
            }

            val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_player)
                .setContentIntent(contentIntent)
                .setOnlyAlertOnce(true)
                .setContentTitle(title)
                .setCustomContentView(simpleContentView)
                .setCustomBigContentView(bigContentView)
                .build()

            val isPaused = false

            simpleContentView.apply {
                setViewVisibility(R.id.player_next, View.VISIBLE)
                setViewVisibility(R.id.player_previous, View.VISIBLE)
                setViewVisibility(R.id.player_progress_bar, View.GONE)

                setTextViewText(R.id.player_song_name, title)
                setTextViewText(R.id.player_author_name, summary)
                setImageViewResource(R.id.player_album_art, R.mipmap.bg_album_default)

                setViewVisibility(R.id.player_play, if (isPaused) View.VISIBLE else View.GONE)
                setViewVisibility(R.id.player_pause, if (isPaused) View.GONE else View.VISIBLE)
            }

            bigContentView.apply {
                setViewVisibility(R.id.player_next, View.VISIBLE)
                setViewVisibility(R.id.player_previous, View.VISIBLE)
                setViewVisibility(R.id.player_progress_bar, View.GONE)

                setTextViewText(R.id.player_song_name, title)
                setTextViewText(R.id.player_author_name, summary)
                setImageViewResource(R.id.player_album_art, R.mipmap.bg_album_default)

                setViewVisibility(R.id.player_play, if (isPaused) View.VISIBLE else View.GONE)
                setViewVisibility(R.id.player_pause, if (isPaused) View.GONE else View.VISIBLE)
            }

            setListeners(simpleContentView)
            setListeners(bigContentView)

            notification.flags = notification.flags or Notification.FLAG_ONGOING_EVENT

            startForeground(5, notification)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setListeners(view: RemoteViews) {
        try {
            var pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                0, Intent(NOTIFY_PREVIOUS).setPackage(packageName),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            view.setOnClickPendingIntent(R.id.player_previous, pendingIntent)
            pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                0, Intent(NOTIFY_CLOSE).setPackage(packageName),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            view.setOnClickPendingIntent(R.id.player_close, pendingIntent)
            pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                0, Intent(NOTIFY_PAUSE).setPackage(packageName),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            view.setOnClickPendingIntent(R.id.player_pause, pendingIntent)
            pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                0, Intent(NOTIFY_NEXT).setPackage(packageName),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            view.setOnClickPendingIntent(R.id.player_next, pendingIntent)
            pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                0, Intent(NOTIFY_PLAY).setPackage(packageName),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            view.setOnClickPendingIntent(R.id.player_play, pendingIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun requestAlbumCover(coverUrl: String, musicId: String) {

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun createActivityWithRouter(path: String): Activity {
        val activity = ARouter.getInstance().build(path).navigation() as Activity
        Timber.tag("Tobin").e("createActivityWithRouter %s", activity.toString())
        return activity
    }
}