package com.pankaj.volvoweather.network

import com.pankaj.volvoweather.LocationCodes
import com.pankaj.volvoweather.data.WeatherData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("location/search/?")
    fun getLocationCodes(@Query("query") location: String): Call<List<LocationCodes>>

    @GET("location/{woeid}")
    fun getWeatherReport(@Path("woeid") code: Int): Call<WeatherData>

    companion object {
        fun create(): ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl("https://www.metaweather.com/api/")
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}