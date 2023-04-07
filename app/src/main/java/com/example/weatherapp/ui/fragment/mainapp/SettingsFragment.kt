package com.example.weatherapp.ui.fragment.mainapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.source.local.sharedpreference.SettingsManager
import com.example.weatherapp.databinding.FragmentSettingsBinding
import com.example.weatherapp.ui.activity.MainActivity

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
            else                              ->    settingsManager.setUserSettingsMapEnabled()
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



    fun NavController.navigateUpOrFinish(activity: FragmentActivity): Boolean {
        return if (navigateUp()) {
            true
        } else {
            activity.finish()
            true
        }
    }

}