package com.example.weatherapp.data.repository

import com.example.weatherapp.data.source.IWeatherRemoteDataSource
import com.example.weatherapp.data.source.remote.response.CurrentWeatherResponse
import com.example.weatherapp.data.source.remote.response.HourlyForecastResponse
import com.example.weatherapp.data.source.remote.response.WeatherOneCallResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Main-safe repository that retrieves data from the network
 * // TODO make sure you receive an IO dispatcher so the repo is main-safe
 * */
class WeatherRepository(
    private val dispatcher: CoroutineDispatcher,
    private val weatherNetwork: IWeatherRemoteDataSource
) {
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
}