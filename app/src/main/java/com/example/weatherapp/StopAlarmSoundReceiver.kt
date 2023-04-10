package com.example.weatherapp

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.weatherapp.util.TAG

class StopAlarmSoundReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val alarmMediaPlayerManager = AlarmMediaPlayerManager.getInstance(p0!!)
        alarmMediaPlayerManager.stop()
    }
}
