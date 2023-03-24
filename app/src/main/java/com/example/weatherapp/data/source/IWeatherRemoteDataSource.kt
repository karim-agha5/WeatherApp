package com.example.weatherapp.data.source

import com.example.weatherapp.data.source.remote.response.CurrentWeatherResponse
import com.example.weatherapp.data.source.remote.response.HourlyForecastResponse
import com.example.weatherapp.data.source.remote.response.WeatherOneCallResponse
import com.example.weatherapp.data.source.remote.response.dataclass.WeatherInfo

/**
 * Main entry point for fetching remote weather response from the API
 * //TODO delete later if useless
 * */

interface IWeatherRemoteDataSource {

    suspend fun getCurrentWeather() : WeatherInfo
    suspend fun getThreeHourForecastForFiveDays() : WeatherInfo
    suspend fun getHourlyForecastForFourDays() : WeatherInfo
    suspend fun getDailyForecastForSixteenDays() : WeatherInfo
    suspend fun getClimaticForecastForAMonth() : WeatherInfo
    suspend fun hourlyForecast(lat: String, lon: String) : HourlyForecastResponse
    suspend fun currentWeatherForecast(lat: String, lon: String) : CurrentWeatherResponse
    suspend fun weatherOneCall(lat: String, lon: String) : WeatherOneCallResponse
}