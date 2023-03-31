package com.example.weatherapp.data.source.local.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherapp.helper.*

/**
 * Manages the global app settings, such as: whether the OnBoarding should be displayed or not,
 * or saving and retrieving user settings.
 * */
class SettingsManager private constructor(val context: Context){

    private val USER_SETTINGS_GPS_KEY = "GPS_Key"
    private val USER_SETTINGS_GPS_VALUE = "GPS_Value"
    private val USER_SETTINGS_MAP_KEY = "Map_Key"
    private val USER_SETTINGS_MAP_VALUE = "Map_Value"
    private val USER_SETTINGS_CELSIUS_KEY = "Celsius_Key"
    private val USER_SETTINGS_CELSIUS_VALUE = "Celsius_Value"
    private val USER_SETTINGS_KELVIN_KEY = "Kelvin_Key"
    private val USER_SETTINGS_KELVIN_VALUE = "Kelvin_Value"
    private val USER_SETTINGS_FAHRENHEIT_KEY = "Fahrenheit_Key"
    private val USER_SETTINGS_FAHRENHEIT_VALUE = "Fahrenheit_Value"
    private val USER_SETTINGS_METER_PER_SEC_KEY = "meter/sec_Key"
    private val USER_SETTINGS_METER_PER_SEC_VALUE = "meter/sec_Value"
    private val USER_SETTINGS_MILES_PER_HOUR_KEY = "miles/hour_Key"
    private val USER_SETTINGS_MILES_PER_HOUR_VALUE = "miles/hour_Value"
    private val USER_SETTINGS_ENGLISH_KEY = "English_Key"
    private val USER_SETTINGS_ENGLISH_VALUE = "English_Value"
    private val USER_SETTINGS_ARABIC_KEY = "Arabic_Key"
    private val USER_SETTINGS_ARABIC_VALUE = "Arabic_Value"
    private val USER_SETTINGS_IS_NOTIFICATIONS_ENABLED = "isEnabled"



    companion object {

        //TODO possible memory leak
        @Volatile
        private var instance: SettingsManager? = null

        fun getInstance(context: Context): SettingsManager {
            return instance ?: synchronized(this) {
                val tempInstance = SettingsManager(context)
                instance = tempInstance
                return tempInstance
            }
        }
    }


    /**
     * Sets whether the onboarding screen is finished or not in its appropriate SharedPreference.
     * */
    fun setOnBoardingFinished(isFinished: Boolean) {
        context
            .getSharedPreferences(ONBOARDING_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(ONBOARDING_SHARED_PREFERENCE_KEY, isFinished)
            .apply()
    }


    /**
     * @return boolean indicating whether the onboarding screen was finished or not.
     * */
    fun isOnBoardingFinished(): Boolean {
        return context
            .getSharedPreferences(ONBOARDING_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
            .getBoolean(ONBOARDING_SHARED_PREFERENCE_KEY, false)
    }

    /**
     * Sets whether the initla user settings screen that is right after the onboarding screen is finished
     * or not in its appropriate SharedPreference.
     * */
    fun setInitialUserSettingsFinished(isFinished: Boolean){
        context
            .getSharedPreferences(INITIAL_USER_SETTINGS_SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE)
            .edit()
            .putBoolean(INITIAL_USER_SETTINGS_SHARED_PREFERENCE_KEY,isFinished)
            .apply()
    }


    /**
     * @return boolean indicating whether the initial user settings screen that is right after the onboarding
     * screen was finished or not.
     * */
    fun isInitialUserSettingsFinished(): Boolean{
       return context
            .getSharedPreferences(INITIAL_USER_SETTINGS_SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE)
            .getBoolean(INITIAL_USER_SETTINGS_SHARED_PREFERENCE_KEY,false)
    }

    /**
     * @return The SharedPreferences object associated with the user settings.
     * */
    private fun getUserSettingsSharedPreferencesEditor(): SharedPreferences.Editor {
        return context.getSharedPreferences(
            INITIAL_USER_SETTINGS_SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        ).edit()
    }

    /**
     * @return whether theSharedPreferences object associated with the user settings
     * contains a certain value or not.
     * */
    private fun userSettingsSharedPreferencesContains(keyName: String): Boolean {
        return context
            .getSharedPreferences(INITIAL_USER_SETTINGS_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
            .contains(keyName)
    }

    /**
     * Removes a value from the SharedPreferences object associated with the user settings.
     **/
    private fun deleteUserSettingsSharedPreferencesEntry(keyName: String) {
        if (userSettingsSharedPreferencesContains(keyName))
            getUserSettingsSharedPreferencesEditor()
                .remove(keyName)
                .apply()
    }


    fun setUserSettingsGpsEnabled() {
        if (userSettingsSharedPreferencesContains(USER_SETTINGS_MAP_KEY)) {
            getUserSettingsSharedPreferencesEditor().remove(USER_SETTINGS_MAP_KEY)
        }
        getUserSettingsSharedPreferencesEditor().putString(
            USER_SETTINGS_GPS_KEY,
            USER_SETTINGS_GPS_VALUE
        ).apply()
    }

    fun setUserSettingsMapEnabled() {
        if (userSettingsSharedPreferencesContains(USER_SETTINGS_GPS_KEY)) {
            deleteUserSettingsSharedPreferencesEntry(USER_SETTINGS_GPS_KEY)
        }
        getUserSettingsSharedPreferencesEditor().putString(
            USER_SETTINGS_MAP_KEY,
            USER_SETTINGS_MAP_VALUE
        ).apply()
    }

    fun setUserSettingsCelsiusEnabled() {
        if (userSettingsSharedPreferencesContains(USER_SETTINGS_KELVIN_KEY)) {
            deleteUserSettingsSharedPreferencesEntry(USER_SETTINGS_KELVIN_KEY)
        } else if (userSettingsSharedPreferencesContains(USER_SETTINGS_FAHRENHEIT_KEY)) {
            deleteUserSettingsSharedPreferencesEntry(USER_SETTINGS_FAHRENHEIT_KEY)
        }
        getUserSettingsSharedPreferencesEditor().putString(
            USER_SETTINGS_CELSIUS_KEY,
            USER_SETTINGS_CELSIUS_VALUE
        ).apply()
    }

    fun setUserSettingsKelvinEnabled() {
        if (userSettingsSharedPreferencesContains(USER_SETTINGS_CELSIUS_KEY)) {
            deleteUserSettingsSharedPreferencesEntry(USER_SETTINGS_CELSIUS_KEY)
        } else if (userSettingsSharedPreferencesContains(USER_SETTINGS_FAHRENHEIT_KEY)) {
            deleteUserSettingsSharedPreferencesEntry(USER_SETTINGS_FAHRENHEIT_KEY)
        }
        getUserSettingsSharedPreferencesEditor().putString(
            USER_SETTINGS_KELVIN_KEY,
            USER_SETTINGS_KELVIN_VALUE
        ).apply()
    }

    fun setUserSettingsFahrenheitEnabled() {
        if (userSettingsSharedPreferencesContains(USER_SETTINGS_KELVIN_KEY)) {
            deleteUserSettingsSharedPreferencesEntry(USER_SETTINGS_KELVIN_KEY)
        } else if (userSettingsSharedPreferencesContains(USER_SETTINGS_CELSIUS_KEY)) {
            deleteUserSettingsSharedPreferencesEntry(USER_SETTINGS_CELSIUS_KEY)
        }
        getUserSettingsSharedPreferencesEditor().putString(
            USER_SETTINGS_FAHRENHEIT_KEY,
            USER_SETTINGS_FAHRENHEIT_VALUE
        ).apply()
    }

    fun setUserSettingsMeterPerSecEnabled() {
        if (userSettingsSharedPreferencesContains(USER_SETTINGS_MILES_PER_HOUR_KEY)) {
            deleteUserSettingsSharedPreferencesEntry(USER_SETTINGS_MILES_PER_HOUR_KEY)
        }
        getUserSettingsSharedPreferencesEditor().putString(
            USER_SETTINGS_METER_PER_SEC_KEY,
            USER_SETTINGS_METER_PER_SEC_VALUE
        ).apply()
    }

    fun setUserSettingsMilesPerHourEnabled() {
        if (userSettingsSharedPreferencesContains(USER_SETTINGS_METER_PER_SEC_KEY)) {
            deleteUserSettingsSharedPreferencesEntry(USER_SETTINGS_METER_PER_SEC_KEY)
        }
        getUserSettingsSharedPreferencesEditor().putString(
            USER_SETTINGS_MILES_PER_HOUR_KEY,
            USER_SETTINGS_MILES_PER_HOUR_VALUE
        ).apply()
    }

    fun setUserSettingsEnglishEnabled() {
        if (userSettingsSharedPreferencesContains(USER_SETTINGS_ARABIC_KEY)) {
            deleteUserSettingsSharedPreferencesEntry(USER_SETTINGS_ARABIC_KEY)
        }
        getUserSettingsSharedPreferencesEditor().putString(
            USER_SETTINGS_ENGLISH_KEY,
            USER_SETTINGS_ENGLISH_VALUE
        ).apply()
    }

    fun setUserSettingsArabicEnabled() {
        if (userSettingsSharedPreferencesContains(USER_SETTINGS_ENGLISH_KEY)) {
            deleteUserSettingsSharedPreferencesEntry(USER_SETTINGS_ENGLISH_KEY)
        }
        getUserSettingsSharedPreferencesEditor().putString(
            USER_SETTINGS_ARABIC_KEY,
            USER_SETTINGS_ARABIC_VALUE
        ).apply()
    }

    fun setUserSettingsNotificationsIsEnabled(isEnabled: Boolean) {
        getUserSettingsSharedPreferencesEditor().putBoolean(
            USER_SETTINGS_IS_NOTIFICATIONS_ENABLED,
            isEnabled
        ).apply()
    }

    fun isUserSettingsLocationSetToGps() =
        userSettingsSharedPreferencesContains(USER_SETTINGS_GPS_KEY)

    fun isUserSettingsLocationSetToMap() =
        userSettingsSharedPreferencesContains(USER_SETTINGS_MAP_KEY)

    fun isUserSettingsTemperatureSetToCelsius() =
        userSettingsSharedPreferencesContains(USER_SETTINGS_CELSIUS_KEY)

    fun isUserSettingsTemperatureSetToKelvin() =
        userSettingsSharedPreferencesContains(USER_SETTINGS_KELVIN_KEY)

    fun isUserSettingsTemperatureSetToFahrenheit() =
        userSettingsSharedPreferencesContains(USER_SETTINGS_FAHRENHEIT_KEY)

    fun isUserSettingsWindSpeedSetToMeterPerSec() =
        userSettingsSharedPreferencesContains(USER_SETTINGS_METER_PER_SEC_KEY)

    fun isUserSettingsWindSpeedSetToMilesPerHour() =
        userSettingsSharedPreferencesContains(USER_SETTINGS_MILES_PER_HOUR_KEY)

    fun isUserSettingsLanguageSetToEnglish() =
        userSettingsSharedPreferencesContains(USER_SETTINGS_ENGLISH_KEY)

    fun isUserSettingsLanguageSetToArabic() =
        userSettingsSharedPreferencesContains(USER_SETTINGS_ARABIC_KEY)

    fun isUserSettingsNotificationsEnabled() =
        userSettingsSharedPreferencesContains(USER_SETTINGS_IS_NOTIFICATIONS_ENABLED)

}