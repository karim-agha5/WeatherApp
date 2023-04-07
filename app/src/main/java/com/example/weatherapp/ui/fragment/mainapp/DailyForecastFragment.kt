package com.example.weatherapp.ui.fragment.mainapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.data.source.local.sharedpreference.SettingsManager
import com.example.weatherapp.data.source.remote.response.daily.DailyWeatherInfo
import com.example.weatherapp.databinding.FragmentDailyForecastBinding
import com.example.weatherapp.ui.adapter.DailyWeatherAdapter
import com.example.weatherapp.util.Converter
import com.example.weatherapp.viewmodel.WeatherInfoViewModel

class DailyForecastFragment : Fragment() {

    private lateinit var binding: FragmentDailyForecastBinding
    private lateinit var weatherInfoViewModel: WeatherInfoViewModel
    private val settingsManager by lazy {
        SettingsManager.getInstance(requireContext())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_daily_forecast,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherInfoViewModel = ViewModelProvider(requireActivity()).get(WeatherInfoViewModel::class.java)

        /*
        * Once the Activity hierarchy gets inflated and this fragment's view hierarchy gets inflated, as well.
        * Get the LiveData containing the daily weather forecast list and observe it.
        * The Activity is responsible for providing the lat, lon of the location.
        * */
        weatherInfoViewModel.weatherOneCallResponse.observe(viewLifecycleOwner, Observer {
            val adapter = DailyWeatherAdapter(settingsManager,requireActivity())
            binding.dailyWeatherAdapter = adapter
            adapter.submitList(it.dailyForecast)
        })
    }


}