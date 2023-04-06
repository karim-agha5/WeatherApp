package com.example.weatherapp.viewmodel

import android.app.Application
import android.location.Address
import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.data.source.remote.response.WeatherOneCallResponse
import com.example.weatherapp.data.source.remote.response.daily.DailyWeatherInfo
import com.example.weatherapp.data.source.remote.response.hourly.HourlyWeatherInfo
import com.example.weatherapp.data.source.remote.service.LocationService
import com.example.weatherapp.data.source.remote.service.RetrofitWeatherNetwork
import com.example.weatherapp.ui.activity.MainActivity
import com.example.weatherapp.util.TAG
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherInfoViewModel(application: Application) : AndroidViewModel(application){

    // Remove the WeatherRepository dependency and inject it later on
    private val locationService: LocationService = LocationService(application.applicationContext)
    private var weatherRepository = WeatherRepository(Dispatchers.IO,RetrofitWeatherNetwork)
    private var _weatherOneCallResponse: MutableLiveData<WeatherOneCallResponse> = MutableLiveData()
    private val _selectedWeatherInfo: MutableLiveData<DailyWeatherInfo> = MutableLiveData()
    private val _selectedListOfWeatherHourlyInfo: MutableLiveData<List<HourlyWeatherInfo>> = MutableLiveData()
    private val _address: MutableLiveData<MutableList<Address>?> = MutableLiveData()
    private var mapLatLng: LatLng = LatLng(0.0,0.0)
    var weatherOneCallResponse: LiveData<WeatherOneCallResponse> = _weatherOneCallResponse
    val selectedWeatherInfo: LiveData<DailyWeatherInfo> = _selectedWeatherInfo
    val selectedListOfWeatherHourlyInfo: LiveData<List<HourlyWeatherInfo>> = _selectedListOfWeatherHourlyInfo
    val address: LiveData<MutableList<Address>?> = _address //TODO should it be private ???

    fun weatherOneCall(lat: String, lon: String) : LiveData<WeatherOneCallResponse> {
        viewModelScope.launch(Dispatchers.Main){
            _weatherOneCallResponse.value = weatherRepository.weatherOneCall(lat, lon)
        }
        return weatherOneCallResponse
    }

    fun getAddress(lat: Double, lon: Double) : LiveData<MutableList<Address>?> {
        viewModelScope.launch(Dispatchers.IO) {
            var list: MutableList<Address>? = locationService.getAddresses(lat, lon)
            if(list?.size == 0) list = null // Prevents ArrayIndexOutOfBoundsException
            _address.postValue(list)
        }
        return address
    }

    fun setMapLatLng(latLng: LatLng){
        mapLatLng = latLng
    }
    fun getMapLatLng() = mapLatLng

    fun setSelectedWeatherInfo(dailyWeatherInfo: DailyWeatherInfo){
        _selectedWeatherInfo.value = dailyWeatherInfo
    }

    fun setSelectedListOfWeatherHourlyInfo(selectedListOfWeatherHourlyInfo: List<HourlyWeatherInfo>){
        _selectedListOfWeatherHourlyInfo.value = selectedListOfWeatherHourlyInfo
    }
}