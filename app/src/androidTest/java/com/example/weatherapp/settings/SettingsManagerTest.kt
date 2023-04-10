package com.example.weatherapp.settings

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.weatherapp.data.source.local.sharedpreference.SettingsManager
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4



@RunWith(JUnit4::class)
class SettingsManagerTest {

    lateinit var instrumentationContext: Context

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context

    }

    @Test
    fun setUserSettingsGpsEnabled_gpsEnabled_isUserSettingsLocationSetToGpsEqualsTrue_True(){
        // Given -> Enabling the Map
        val settingsManager = SettingsManager.getInstance(instrumentationContext)

        // Where -> call the setUserSettingsGpsEnabled() on the settingsManager
        settingsManager.setUserSettingsGpsEnabled()

        // Then -> We should be getting true on the isUserSettingsLocationSetToGps()
        Assert.assertTrue(settingsManager.isUserSettingsLocationSetToGps())
    }

    @Test
    fun setUserSettingsGpsEnabled_mapEnabled_isUserSettingsLocationSetToGpsEqualsFalse_True(){
        // Given -> SettingsManager
        val settingsManager = SettingsManager.getInstance(instrumentationContext)

        // Where -> call the setUserSettingsMapEnabled() on the settingsManager
        settingsManager.setUserSettingsMapEnabled()

        // Then -> We should be getting false to isUserSettingsLocationSetToGps()
        assertFalse(settingsManager.isUserSettingsLocationSetToGps())
    }

    @Test
    fun getInstance_twoSingletonInstances_bothHashCodesMatch(){
        // Given -> Two SettingsManager references
        var settingsManager1: SettingsManager? = null
        var settingsManager2: SettingsManager? = null


        //Where -> call the getInstance(context) method on their class
        settingsManager1 = SettingsManager.getInstance(instrumentationContext)
        settingsManager2 = SettingsManager.getInstance(instrumentationContext)

        // Then -> We should be getting matching hash codes
        assertThat(settingsManager1.hashCode(), Matchers.`is`(settingsManager2.hashCode()))
    }
}