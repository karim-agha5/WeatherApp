package com.example.weatherapp.data.source.local.room

import androidx.room.*
import com.example.weatherapp.data.source.remote.response.WeatherOneCallResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherOneCallResponseDao {

    /*@TypeConverters(EntityConverter::class)
    @Query("SELECT * FROM weatherOneCallResponse WHERE lat != 0.0 AND lon != 0.0")
    suspend fun getAll() : List<WeatherOneCallResponse>
*/

    @TypeConverters(EntityConverter::class)
    @Query("SELECT * FROM weatherOneCallResponse WHERE lat != 0.0 AND lon != 0.0")
    fun getAllFavoriteProducts() : Flow<List<WeatherOneCallResponse>>


    @TypeConverters(EntityConverter::class)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteLocation(weatherOneCallResponse: WeatherOneCallResponse)

    @TypeConverters(EntityConverter::class)
    @Delete
    suspend fun deleteFavoriteLocation(weatherOneCallResponse: WeatherOneCallResponse)
}