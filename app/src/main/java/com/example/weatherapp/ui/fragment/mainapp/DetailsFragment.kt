package com.example.weatherapp.ui.fragment.mainapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.*
import com.example.weatherapp.data.source.remote.response.hourly.HourlyWeatherInfo
import com.example.weatherapp.databinding.FragmentDetailsBinding
import com.example.weatherapp.util.*
import com.example.weatherapp.ui.adapter.HourlyForecastAdapter
import com.example.weatherapp.viewmodel.WeatherInfoViewModel

class DetailsFragment : Fragment() {

    private lateinit var fragmentDetailsBinding: FragmentDetailsBinding
    private val weatherInfoViewModel: WeatherInfoViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentDetailsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false)
        return fragmentDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var adapter = HourlyForecastAdapter()
        fragmentDetailsBinding.rvCurrentHourlyForecast.layoutManager =
            LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        /*
        * The reason we check for the nullability of the value of the exposed LiveData is that
        * the details fragment gets instantiated once the Host activity gets created.
        * At that time, There's no way *I think* the data has been fetched yet.
        * */


        // If the data is still being fetched and the details tab is clicked
        if(weatherInfoViewModel.selectedWeatherInfo.value == null){
            weatherInfoViewModel.selectedWeatherInfo.observe(viewLifecycleOwner){
                fragmentDetailsBinding.tvHumidityValue.text = getHumidityUnit(it.humidity)
                fragmentDetailsBinding.tvPressureValue.text = getPressureUnit(it.pressure)
                fragmentDetailsBinding.tvWindSpeedValue.text = getWindSpeedUnit(it.windSpeed,true)
                fragmentDetailsBinding.tvCloudsValue.text = getCloudsUnit(it.clouds)
            }
        }

        // If the data is already fetched.
        else{
            fragmentDetailsBinding.tvHumidityValue.text = getHumidityUnit(weatherInfoViewModel?.selectedWeatherInfo?.value?.humidity ?: 0)
            fragmentDetailsBinding.tvPressureValue.text = getPressureUnit(weatherInfoViewModel?.selectedWeatherInfo?.value?.pressure ?: 0)
            fragmentDetailsBinding.tvWindSpeedValue.text = getWindSpeedUnit((weatherInfoViewModel?.selectedWeatherInfo?.value?.windSpeed ?: 0.0) as Float,true)
            fragmentDetailsBinding.tvCloudsValue.text = getCloudsUnit(weatherInfoViewModel?.selectedWeatherInfo?.value?.clouds ?: 0)
        }

        // MAKE SURE TO SEND THE HOURLY FORECASTS AS WELL
        if(weatherInfoViewModel.selectedListOfWeatherHourlyInfo.value == null){
            weatherInfoViewModel.selectedListOfWeatherHourlyInfo.observe(viewLifecycleOwner){
                val list: List<HourlyWeatherInfo> = getOneDayWeatherInfo(it)
                adapter.submitList(list)
                fragmentDetailsBinding.rvCurrentHourlyForecast.adapter = adapter
            }
        }

        else{
            val list: List<HourlyWeatherInfo> =
                getOneDayWeatherInfo(weatherInfoViewModel?.selectedListOfWeatherHourlyInfo?.value ?: listOf())
            adapter.submitList(list)
            fragmentDetailsBinding.hourlyForecastAdapter = adapter
            adapter.notifyDataSetChanged() // TODO probably unneccessary
        }

    }

    private fun getOneDayWeatherInfo(list: List<HourlyWeatherInfo>) : List<HourlyWeatherInfo>{
        val resultList: MutableList<HourlyWeatherInfo> = mutableListOf()
        for(i in 0 .. 24){
            resultList.add(list[i])
        }
        return resultList.toList()
    }
}