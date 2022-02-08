package com.pankaj.volvoweather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pankaj.volvoweather.LocationCodes
import com.pankaj.volvoweather.data.WeatherData
import com.pankaj.volvoweather.network.ApiInterface
import retrofit2.Call
import retrofit2.Response

class VolvoWeatherViewModel : ViewModel() {

    val TAG = "VolvoWeatherViewModel"

    private val _locationCodes = MutableLiveData<List<LocationCodes>>()
    val locationCodes: LiveData<List<LocationCodes>>
        get() = _locationCodes

    private val _weatherData = MutableLiveData<List<WeatherData>>()
    val weatherData: LiveData<List<WeatherData>>
        get() = _weatherData

    private var _serverError = MutableLiveData<Boolean>(false)
    val serverError: LiveData<Boolean>
        get() = _serverError

    val apiInterface by lazy {
        ApiInterface.create()
    }

    fun fetchLocationData(location: ArrayList<String>) {
        var locationCodesList: ArrayList<LocationCodes> = ArrayList()
        for (loc in location) {
            val inter = apiInterface.getLocationCodes(loc)
            inter.enqueue(object : retrofit2.Callback<List<LocationCodes>> {
                override fun onResponse(
                    call: Call<List<LocationCodes>>,
                    response: Response<List<LocationCodes>>
                ) {
                    Log.d(TAG, "result = " + response.body())
                    response.body()?.let { locationCodesList.add(it.get(0)) }
                    if (location.size == locationCodesList.size) {
                        // we have recieved location code for all cities
                        _locationCodes.value = locationCodesList
                    }
                }

                override fun onFailure(call: Call<List<LocationCodes>>, t: Throwable) {
                    Log.e(TAG, "fetch location code api failed")
                    _serverError.value = true
                }
            })
        }
    }

    fun fetchWeatherReport(codeList: ArrayList<Int>) {
        var weatherInfoList: ArrayList<WeatherData> = ArrayList()
        for (code in codeList) {
            val inter = apiInterface.getWeatherReport(code)
            inter.enqueue(object : retrofit2.Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    Log.d(TAG, "result = " + response.body())
                    response.body()?.let { weatherInfoList.add(it) }
                    if (codeList.size == weatherInfoList.size) {
                        // we have recieved weeather report for all cities
                        _weatherData.value = weatherInfoList
                    }
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    Log.e(TAG, "fetch weather report api failed")
                    _serverError.value = true
                }
            })
        }
    }
}