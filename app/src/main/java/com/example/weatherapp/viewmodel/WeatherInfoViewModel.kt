package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.TAG
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.data.source.remote.response.WeatherOneCallResponse
import com.example.weatherapp.data.source.remote.response.daily.DailyWeatherInfo
import com.example.weatherapp.data.source.remote.response.hourly.HourlyWeatherInfo
import com.example.weatherapp.data.source.remote.service.RetrofitWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherInfoViewModel() : ViewModel(){

    // Remove the WeatherRepository dependency and inject it later on
    private var weatherRepository = WeatherRepository(Dispatchers.IO,RetrofitWeatherNetwork)
    private var _weatherOneCallResponse: MutableLiveData<WeatherOneCallResponse> = MutableLiveData()
    private val _selectedWeatherInfo: MutableLiveData<DailyWeatherInfo> = MutableLiveData()
    private val _selectedListOfWeatherHourlyInfo: MutableLiveData<List<HourlyWeatherInfo>> = MutableLiveData()
    var weatherOneCallResponse: LiveData<WeatherOneCallResponse> = _weatherOneCallResponse
    val selectedWeatherInfo: LiveData<DailyWeatherInfo> = _selectedWeatherInfo
    val selectedListOfWeatherHourlyInfo: LiveData<List<HourlyWeatherInfo>> = _selectedListOfWeatherHourlyInfo

    fun weatherOneCall(lat: String, lon: String) : LiveData<WeatherOneCallResponse> {
        viewModelScope.launch(Dispatchers.Main){
            _weatherOneCallResponse.value = weatherRepository.weatherOneCall(lat, lon)
        }
        return weatherOneCallResponse
    }

    fun setSelectedWeatherInfo(dailyWeatherInfo: DailyWeatherInfo){
        _selectedWeatherInfo.value = dailyWeatherInfo
    }

    fun setSelectedListOfWeatherHourlyInfo(selectedListOfWeatherHourlyInfo: List<HourlyWeatherInfo>){
        _selectedListOfWeatherHourlyInfo.value = selectedListOfWeatherHourlyInfo
    }
}