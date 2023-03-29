package com.example.weatherapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.weatherapp.*
import com.example.weatherapp.databinding.FragmentDetailsBinding
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
       // return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        }

        // MAKE SURE TO SEND THE HOURLY FORECASTS AS WELL
        if(weatherInfoViewModel.selectedListOfWeatherHourlyInfo.value == null){
            weatherInfoViewModel.selectedListOfWeatherHourlyInfo.observe(viewLifecycleOwner){
                fragmentDetailsBinding.rvCurrentHourlyForecast.layoutManager =
                    LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                val adapter = HourlyForecastAdapter()
                adapter.submitList(it)
                fragmentDetailsBinding.rvCurrentHourlyForecast.adapter = adapter
            }
        }

        else{

        }

        Log.i(TAG, "DetailsFragment ${weatherInfoViewModel.hashCode()} ")
    }
}