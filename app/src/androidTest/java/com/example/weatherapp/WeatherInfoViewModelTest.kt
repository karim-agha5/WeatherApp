package com.example.weatherapp

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.weatherapp.viewmodel.WeatherInfoViewModel
import getOrAwaitValue
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock


@RunWith(JUnit4::class)
class WeatherInfoViewModelTest {

    @Mock
    private lateinit var application: Application

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var weatherInfoViewModel: WeatherInfoViewModel

    @Before
    fun setup(){
        application = ApplicationProvider.getApplicationContext()
        weatherInfoViewModel = WeatherInfoViewModel(application)
    }

    @Test
    fun weatherOneCall_weatherOneCallResponseContainsAValue(){
        // Given -> The latitude and longitude values of Egypt
        val egLat = "26.8206"
        val egLon = "30.8025"

        // When -> Call the weatherOneCall() method
        weatherInfoViewModel.weatherOneCall(egLat,egLon)


        // Then -> The weatherOneCallResponse should contain the value returned by the API
        val result = weatherInfoViewModel.weatherOneCallResponse.getOrAwaitValue {}
        assertThat(result, CoreMatchers.not(CoreMatchers.nullValue()))
    }
}