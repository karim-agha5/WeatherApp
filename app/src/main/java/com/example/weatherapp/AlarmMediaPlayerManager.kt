package com.example.weatherapp

import android.content.Context
import android.media.MediaPlayer
import android.provider.Settings
import android.util.Log
import com.example.weatherapp.util.TAG


class AlarmMediaPlayerManager {

    companion object{
        @Volatile
        var mediaPlayer: MediaPlayer? = null

        fun getInstance(context: Context) : MediaPlayer {
            return mediaPlayer ?: synchronized(this){
                val temp = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI)
                mediaPlayer = temp
                return temp
            }
        }

    }

}