package com.example.weatherapp.data.repository

import android.content.Context
import com.example.weatherapp.data.source.IWeatherRemoteDataSource
import com.example.weatherapp.data.source.local.room.WeatherDatabase
import com.example.weatherapp.data.source.local.room.WeatherOneCallResponseDao
import com.example.weatherapp.data.source.remote.response.CurrentWeatherResponse
import com.example.weatherapp.data.source.remote.response.HourlyForecastResponse
import com.example.weatherapp.data.source.remote.response.WeatherOneCallResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Main-safe repository that retrieves data from the network
 * // TODO make sure you receive an IO dispatcher so the repo is main-safe
 * */
class WeatherRepository(
    private val dispatcher: CoroutineDispatcher,
    private val weatherNetwork: IWeatherRemoteDataSource,
    context: Context
) {

    private val weatherDatabase: WeatherDatabase = WeatherDatabase.getInstance(context)
    private val weatherOneCallResponseDao: WeatherOneCallResponseDao = weatherDatabase.getWeatherOneCallResponseDao()

    private
     suspend fun hourlyForecast(lat: String,lon: String) : HourlyForecastResponse = withContext(dispatcher){
         return@withContext weatherNetwork.hourlyForecast(lat,lon)
     }

    suspend fun currentWeatherForecast(lat: String,lon: String) : CurrentWeatherResponse
    = withContext(dispatcher){
        return@withContext weatherNetwork.currentWeatherForecast(lat, lon)
    }

    suspend fun weatherOneCall(lat: String, lon: String) : WeatherOneCallResponse = withContext(dispatcher){
        return@withContext weatherNetwork.weatherOneCall(lat, lon)
    }

    suspend fun getAllFavoriteLocations() : Flow<List<WeatherOneCallResponse>> =
        withContext(dispatcher){
            return@withContext weatherOneCallResponseDao.getAllFavoriteProducts()
        }

    suspend fun insertFavoriteLocation(weatherOneCallResponse: WeatherOneCallResponse) =
        withContext(dispatcher){
            return@withContext weatherOneCallResponseDao.insertFavoriteLocation(weatherOneCallResponse)
        }

    suspend fun deleteFavoriteLocation(weatherOneCallResponse: WeatherOneCallResponse) =
        withContext(dispatcher){
            return@withContext weatherOneCallResponseDao.deleteFavoriteLocation(weatherOneCallResponse)
        }
}