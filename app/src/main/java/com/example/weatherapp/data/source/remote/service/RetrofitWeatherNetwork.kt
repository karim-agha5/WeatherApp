package com.example.weatherapp.data.source.remote.service

import com.example.weatherapp.data.source.IWeatherRemoteDataSource
import com.example.weatherapp.data.source.remote.response.CurrentWeatherResponse
import com.example.weatherapp.data.source.remote.response.HourlyForecastResponse
import com.example.weatherapp.data.source.remote.response.WeatherOneCallResponse
import com.example.weatherapp.data.source.remote.response.dataclass.WeatherInfo
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

const val baseUrl: String = "https://pro.openweathermap.org/data/2.5/forecast/"
const val oneCallBaseUrl: String = "https://api.openweathermap.org/data/2.5/"
const val apikey: String = "837e42e9093bcf2262d52c0b877ba9ce"


/**
 *  Retrofit API declarations for Weather Network API
 * */

private interface WeatherNetworkApi {

    @GET("hourly")
    suspend fun hourlyForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String
    ) : HourlyForecastResponse

    @GET("weather")
    suspend fun currentWeatherForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String
    ) : CurrentWeatherResponse

    @GET("onecall")
    suspend fun weatherOneCall(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String = "minutely",
        @Query("appid") appid: String
    ) : WeatherOneCallResponse

    // DailyForecast
    suspend fun dailyForecast(

    )
}

/**
 * Exposes the concrete implementation of [WeatherNetworkApi]
 * */

object RetrofitWeatherNetwork : IWeatherRemoteDataSource{
    private val client: OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .build()
    private val weatherNetworkApi =
        Retrofit
            .Builder()
            .baseUrl(oneCallBaseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherNetworkApi::class.java)

    override suspend fun hourlyForecast(lat: String, lon: String) : HourlyForecastResponse {
        return weatherNetworkApi.hourlyForecast(lat, lon, apikey)
    }

    override suspend fun currentWeatherForecast(lat: String, lon: String) : CurrentWeatherResponse{
        return weatherNetworkApi.currentWeatherForecast(lat, lon, apikey)
    }

    override suspend fun weatherOneCall(lat: String, lon: String) : WeatherOneCallResponse{
        return weatherNetworkApi.weatherOneCall(lat,lon, appid = apikey)
    }

    override suspend fun getCurrentWeather(): WeatherInfo {
        TODO("Not yet implemented")
    }

    override suspend fun getThreeHourForecastForFiveDays(): WeatherInfo {
        TODO("Not yet implemented")
    }

    override suspend fun getHourlyForecastForFourDays(): WeatherInfo {
        TODO("Not yet implemented")
    }

    override suspend fun getDailyForecastForSixteenDays(): WeatherInfo {
        TODO("Not yet implemented")
    }

    override suspend fun getClimaticForecastForAMonth(): WeatherInfo {
        TODO("Not yet implemented")
    }
}