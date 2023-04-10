package com.example.weatherapp


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

const val NOTIFICATION_ID = 1
const val CHANNEL_ID = "Weather Alerts"
const val INTENT_TITLE_KEY = "intentTitle"
const val INTENT_MESSAGE_KEY = "intentMessage"

class Notification : BroadcastReceiver() {


    override fun onReceive(p0: Context?, p1: Intent?) {
        val channelName = "Weather Alerts"
        val description = "Scheduled Weather Alerts"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, channelName, importance)
        channel.description = description

        val notificationManager = p0?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val title = p1?.getStringExtra(INTENT_TITLE_KEY)
        val message = p1?.getStringExtra(INTENT_MESSAGE_KEY)


        val stopAlarmMediaManagerIntent = Intent(p0, StopAlarmSoundReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            p0,
            NOTIFICATION_ID,
            stopAlarmMediaManagerIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder =
            NotificationCompat.Builder(p0, CHANNEL_ID)
                .setSmallIcon(R.drawable.weather_flat_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(importance)
                .setAutoCancel(true)
                .addAction(R.drawable.baseline_add_alert_24, "TURN OFF", pendingIntent)


        val notification = notificationBuilder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)

        val mediaPlayer = AlarmMediaPlayerManager.getInstance(p0)
        mediaPlayer.start()
    }
}