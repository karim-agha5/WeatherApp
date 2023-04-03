package com.example.weatherapp.ui.fragment.initialsetup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import com.example.weatherapp.R
import com.example.weatherapp.data.source.local.sharedpreference.SettingsManager
import com.example.weatherapp.databinding.FragmentInitialUserSettingsBinding
import com.example.weatherapp.util.TAG

/*
TODO Should probably delegate all the save operations to whatever storage to the OnDoneButtonClickListener
* */
class InitialUserSettingsFragment : Fragment() {

    private lateinit var binding: FragmentInitialUserSettingsBinding
    private val onDoneButtonClickListener: OnDoneButtonClickListener by lazy {
        requireActivity() as OnDoneButtonClickListener
    }

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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_initial_user_settings,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnInitialUserSettingsDone.setOnClickListener {
            saveUserInitialSettings(view)
            setInitialUserSettingsFinished()
            onDoneButtonClickListener.onClick()
        }
    }


    private fun saveUserInitialSettings(view: View){
        saveSelectedLocationRadioButton(view)
        saveSelectedTemperatureRadioButton(view)
        saveSelectedWindSpeedRadioButton(view)
        saveSelectedLanguageRadioButton(view)
        saveSelectedNotificationsSettingRadioButton(view)
     //   Log.i(TAG, "initial settings fragment: ${settingsManager.hashCode()}")
    }

    private fun setInitialUserSettingsFinished(){
        settingsManager.setInitialUserSettingsFinished(true)
    }

    private fun saveSelectedLocationRadioButton(view: View){
        // Gets the checked radio button in its radio group
       val radioButton =
           view.findViewById<RadioButton>(binding.rgUserInitialSettingsLocation.checkedRadioButtonId)
        when(radioButton.text){
            resources.getString(R.string.gps) ->    settingsManager.setUserSettingsGpsEnabled()
            else                              ->    settingsManager.setUserSettingsMapEnabled()
        }
    }

    private fun saveSelectedTemperatureRadioButton(view: View){
        // Gets the checked radio button in its radio group
        val radioButton =
            view.findViewById<RadioButton>(binding.rgUserInitialSettingsTemperature.checkedRadioButtonId)
        when(radioButton.text){
            resources.getString(R.string.celsius) ->    settingsManager.setUserSettingsCelsiusEnabled()
            resources.getString(R.string.kelvin)  ->    settingsManager.setUserSettingsKelvinEnabled()
            else                                  ->    settingsManager.setUserSettingsFahrenheitEnabled()
        }
    }

    private fun saveSelectedWindSpeedRadioButton(view: View){
        // Gets the checked radio button in its radio group
        val radioButton =
            view.findViewById<RadioButton>(binding.rgUserInitialSettingsWindSpeed.checkedRadioButtonId)
        when(radioButton.text){
            resources.getString(R.string.meter_per_sec) ->  settingsManager.setUserSettingsMeterPerSecEnabled()
            else                                        ->  settingsManager.setUserSettingsMilesPerHourEnabled()
        }
    }

    private fun saveSelectedLanguageRadioButton(view: View){
        // Gets the checked radio button in its radio group
        val radioButton =
            view.findViewById<RadioButton>(binding.rgUserInitialSettingsLanguage.checkedRadioButtonId)
        when(radioButton.text){
            resources.getString(R.string.english)   ->  settingsManager.setUserSettingsEnglishEnabled()
            else                                    -> settingsManager.setUserSettingsArabicEnabled()
        }
    }

    private fun saveSelectedNotificationsSettingRadioButton(view: View){
        // Gets the checked radio button in its radio group
        val radioButton =
            view.findViewById<RadioButton>(binding.rgUserInitialSettingsNotifications.checkedRadioButtonId)
        when(radioButton.text){
            resources.getString(R.string.enabled)   ->  settingsManager.setUserSettingsNotificationsIsEnabled(true)
            else                                    -> settingsManager.setUserSettingsNotificationsIsEnabled(false)
        }
    }

    interface OnDoneButtonClickListener{
        fun onClick()
    }
}