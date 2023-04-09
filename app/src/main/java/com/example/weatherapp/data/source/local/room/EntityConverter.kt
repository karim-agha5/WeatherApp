package com.example.weatherapp.data.source.local.room

import androidx.room.TypeConverter
import com.example.weatherapp.data.source.remote.response.current.CurrentWeatherInfo
import com.example.weatherapp.data.source.remote.response.daily.DailyWeatherInfo
import com.example.weatherapp.data.source.remote.response.dataclass.Alert
import com.example.weatherapp.data.source.remote.response.dataclass.CustomAddress
import com.example.weatherapp.data.source.remote.response.dataclass.Rain
import com.example.weatherapp.data.source.remote.response.dataclass.Weather
import com.example.weatherapp.data.source.remote.response.hourly.HourlyWeatherInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class EntityConverter {

    @TypeConverter
    fun hourlyListToString(list: List<HourlyWeatherInfo>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun dailyListToString(list: List<DailyWeatherInfo>) : String{
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun currentWeatherInfoToString(currentWeatherInfo: CurrentWeatherInfo) : String{
        val gson = Gson()
        return gson.toJson(currentWeatherInfo)
    }

    @TypeConverter
    fun customAddressToString(customAddress: CustomAddress) : String{
        val gson = Gson()
        return gson.toJson(customAddress)
    }

    @TypeConverter
    fun alertsListToString(list: List<Alert?>?) : String{
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun getCurrentWeatherInfoFromString(jsonString: String) : CurrentWeatherInfo{
        return Gson().fromJson(jsonString, object : TypeToken<CurrentWeatherInfo?>() {}.type)
    }

    @TypeConverter
    fun getCustomAddressFromString(jsonString: String) : CustomAddress{
        return Gson().fromJson(jsonString, object : TypeToken<CustomAddress>() {}.type)
    }

    @TypeConverter
    fun weatherListToString(list: List<Weather>) : String{
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun rainToString(rain: Rain) : String{
        val gson = Gson()
        return gson.toJson(rain)
    }

    @TypeConverter
    fun getRainFromString(jsonString: String) : Rain{
        return Gson().fromJson(jsonString, object : TypeToken<Rain?>() {}.type)
    }

    @TypeConverter
    fun getHourlyListFromString(jsonString: String): List<HourlyWeatherInfo> {
        val listType: Type =
            object : TypeToken<List<HourlyWeatherInfo?>?>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    @TypeConverter
    fun getDailyListFromString(jsonString: String) : List<DailyWeatherInfo>{
        val listType: Type =
            object : TypeToken<List<DailyWeatherInfo?>?>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    @TypeConverter
    fun getWeatherListFromString(jsonString: String) : List<Weather> {
        val listType: Type =
            object : TypeToken<List<Weather?>?>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    @TypeConverter
    fun getAlertsListFromString(jsonString: String) : List<Alert?>? {
        val listType: Type =
            object : TypeToken<List<Alert?>?>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}