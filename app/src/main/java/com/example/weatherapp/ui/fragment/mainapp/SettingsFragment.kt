package com.example.weatherapp.ui.fragment.mainapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.source.local.sharedpreference.SettingsManager
import com.example.weatherapp.databinding.FragmentSettingsBinding
import com.example.weatherapp.ui.activity.MainActivity
import com.example.weatherapp.util.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val settingsManager by lazy {
        SettingsManager.getInstance(requireContext().applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_settings,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigationConfig()
        //initialStateSetup()



        binding.btnUserSettingsDone.setOnClickListener {
            saveUserInitialSettings(view)
            if(settingsManager.isUserSettingsLocationSetToMap()){
                navigateToUnableToFindLocationFragment()
            }
            else{
                navigateToMainFragment()
            }
        }
    }

    private fun setupNavigationConfig(){
        val appBarConfiguration =
            AppBarConfiguration(findNavController().graph,(activity as MainActivity).mainActivityBinding.drawerLayout)

        /*
        * This should transform the hamburger icon to the back button
        * if a non top-level destination is on top of the stack
        * */

       // toolbar.setupWithNavController(findNavController(),appBarConfiguration)
        binding.settingsFragmentToolbar.setupWithNavController(findNavController(),appBarConfiguration)

        /*
        * Attaches the nav controller to the navigation view to enable the navigation among fragments
        * through the menu items
        * */
        (activity as MainActivity).mainActivityBinding.navigationView.setupWithNavController(findNavController())

    }

    /*
    * Sets which RadioButtons to be checked based on the latest saved settings.
    * */
    private fun initialStateSetup(){
          var gps = false
         var map = false
         var cel = false
         var kel = false
         var fah = false
         var meter = false
         var mile = false
         var arabic = false
         var english = false
         var isEnabled = false
         lifecycleScope.launch(Dispatchers.IO) {
            when {
                //Location
                settingsManager
                    .isUserSettingsLocationSetToGps() -> gps = true
                settingsManager
                    .isUserSettingsLocationSetToMap() -> map = true

                // Temperature
                settingsManager
                    .isUserSettingsTemperatureSetToCelsius() -> cel = true
                settingsManager.isUserSettingsTemperatureSetToKelvin() -> kel = true
                settingsManager.isUserSettingsTemperatureSetToFahrenheit() -> fah = true

                // Wind Speed
                settingsManager
                    .isUserSettingsWindSpeedSetToMeterPerSec() -> meter = true
                settingsManager
                    .isUserSettingsWindSpeedSetToMilesPerHour() -> mile = true

                // Language
                settingsManager
                    .isUserSettingsLanguageSetToArabic() -> arabic = true
                settingsManager
                    .isUserSettingsLanguageSetToEnglish() -> english = true

                // Notifications
                settingsManager
                    .isUserSettingsNotificationsEnabled() -> isEnabled = true
            }
             withContext(Dispatchers.Main){
                 when{
                     // Location
                     gps == true -> {
                        /* binding.rbUserSettingsGps.isChecked = true
                         binding.rbUserSettingsMap.isChecked = false*/
                    //     Log.i(TAG, "gps $gps ")
                     }

                     map == true -> {
                        /* binding.rbUserSettingsMap.isChecked = true
                         binding.rbUserSettingsGps.isChecked = false*/
                      //   Log.i(TAG, "map $map ")
                     }


                     // Temperature
                     cel == true -> {
                       /*  binding.rbUserSettingsCelsius.isChecked = true
                         binding.rbUserSettingsKelvin.isChecked = false
                         binding.rbUserSettingsFahrenheit.isChecked = false*/
                         Log.i(TAG, "cel $cel ")
                     }
                     kel == true -> {
                         /*binding.rbUserSettingsKelvin.isChecked = true
                         binding.rbUserSettingsFahrenheit.isChecked = false
                         binding.rbUserSettingsCelsius.isChecked = false*/
                         Log.i(TAG, "kel $kel ")

                     }
                     fah == true -> {
                        /* binding.rbUserSettingsFahrenheit.isChecked = true
                         binding.rbUserSettingsCelsius.isChecked = false
                         binding.rbUserSettingsKelvin.isChecked = false*/
                         Log.i(TAG, "fah $fah ")
                     }


                     // Wind Speed
                     meter == true -> {
                        /* binding.rbUserSettingsMeterPerSec.isChecked = true
                         binding.rbUserSettingsMilesPerHour.isChecked = false*/
                         Log.i(TAG, "meter $meter ")
                     }

                     mile == true -> {
                         /*binding.rbUserSettingsMilesPerHour.isChecked = true
                         binding.rbUserSettingsMeterPerSec.isChecked = false*/
                         Log.i(TAG, "mile $mile ")
                     }


                     // Language
                     arabic == true -> {
                        /* binding.rbUserSettingsArabic.isChecked = true
                         binding.rbUserSettingsEnglish.isChecked = false*/
                         Log.i(TAG, "arabic $arabic ")
                     }

                     english == true -> {
                        /* binding.rbUserSettingsEnglish.isChecked = true
                         binding.rbUserSettingsArabic.isChecked = false*/
                         Log.i(TAG, "english $english ")
                     }

                     // Notifications
                     isEnabled == true -> {
                        /* binding.rbUserSettingsEnabled.isChecked = true
                         binding.rbUserSettingsDisabled.isChecked = false*/
                         Log.i(TAG, "isEnabled $isEnabled ")
                     }

                     isEnabled == false -> {
                         /*binding.rbUserSettingsDisabled.isChecked = true
                         binding.rbUserSettingsEnabled.isChecked = false*/
                         Log.i(TAG, "disabled $isEnabled ")
                     }
                 }
             }
         }
    }

    private fun navigateToUnableToFindLocationFragment(){
        findNavController().navigate(
            SettingsFragmentDirections.actionSettingsFragmentToUnableToFindALocationFragment()
        )
        findNavController().graph.setStartDestination(R.id.unableToFindALocationFragment)
    }

    private fun navigateToMainFragment(){
        findNavController().navigate(
            SettingsFragmentDirections.actionSettingsFragmentToMainFragment()
        )
        findNavController().graph.setStartDestination(R.id.mainFragment)
    }

    private fun saveUserInitialSettings(view: View){
        saveSelectedLocationRadioButton(view)
        saveSelectedTemperatureRadioButton(view)
        saveSelectedWindSpeedRadioButton(view)
        saveSelectedLanguageRadioButton(view)
        saveSelectedNotificationsSettingRadioButton(view)
    }

    private fun saveSelectedLocationRadioButton(view: View){
        // Gets the checked radio button in its radio group
        val radioButton =
            view.findViewById<RadioButton>(binding.rgUserSettingsLocation.checkedRadioButtonId)
        when(radioButton.text){
            resources.getString(R.string.gps) ->    settingsManager.setUserSettingsGpsEnabled()
            else  ->    settingsManager.setUserSettingsMapEnabled()
        }
    }

    private fun saveSelectedTemperatureRadioButton(view: View){
        // Gets the checked radio button in its radio group
        val radioButton =
            view.findViewById<RadioButton>(binding.rgUserSettingsTemperature.checkedRadioButtonId)
        when(radioButton.text){
            resources.getString(R.string.celsius) ->    settingsManager.setUserSettingsCelsiusEnabled()
            resources.getString(R.string.kelvin)  ->    settingsManager.setUserSettingsKelvinEnabled()
            else                                  ->    settingsManager.setUserSettingsFahrenheitEnabled()
        }
    }

    private fun saveSelectedWindSpeedRadioButton(view: View){
        // Gets the checked radio button in its radio group
        val radioButton =
            view.findViewById<RadioButton>(binding.rgUserSettingsWindSpeed.checkedRadioButtonId)
        when(radioButton.text){
            resources.getString(R.string.meter_per_sec) ->  settingsManager.setUserSettingsMeterPerSecEnabled()
            else                                        ->  settingsManager.setUserSettingsMilesPerHourEnabled()
        }
    }

    private fun saveSelectedLanguageRadioButton(view: View){
        // Gets the checked radio button in its radio group
        val radioButton =
            view.findViewById<RadioButton>(binding.rgUserSettingsLanguage.checkedRadioButtonId)
        when(radioButton.text){
            resources.getString(R.string.english)   ->  settingsManager.setUserSettingsEnglishEnabled()
            else                                    -> settingsManager.setUserSettingsArabicEnabled()
        }
    }

    private fun saveSelectedNotificationsSettingRadioButton(view: View){
        // Gets the checked radio button in its radio group
        val radioButton =
            view.findViewById<RadioButton>(binding.rgUserSettingsNotifications.checkedRadioButtonId)
        when(radioButton.text){
            resources.getString(R.string.enabled)   ->  settingsManager.setUserSettingsNotificationsIsEnabled(true)
            else                                    -> settingsManager.setUserSettingsNotificationsIsEnabled(false)
        }
    }


}