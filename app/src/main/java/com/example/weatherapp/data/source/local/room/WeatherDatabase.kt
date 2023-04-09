package com.example.weatherapp.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.data.source.remote.response.WeatherOneCallResponse

@Database(entities = [WeatherOneCallResponse::class], version = 1)
@TypeConverters(EntityConverter::class)
abstract class WeatherDatabase : RoomDatabase(){

    abstract fun getWeatherOneCallResponseDao() : WeatherOneCallResponseDao
    companion object{
        @Volatile
        var instance: WeatherDatabase? = null

        fun getInstance(context: Context) : WeatherDatabase{
            return instance ?: synchronized(this){
                val temp =
                    Room
                        .databaseBuilder(context.applicationContext,WeatherDatabase::class.java,"weatherdb")
                        .build()
                instance = temp
                return temp
            }
        }
    }
}