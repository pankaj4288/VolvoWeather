package com.pankaj.volvoweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pankaj.volvoweather.adapter.WeatherAdapter
import com.pankaj.volvoweather.databinding.ActivityMainBinding
import com.pankaj.volvoweather.viewmodel.VolvoWeatherViewModel

class VolvoWeatherActivity : AppCompatActivity() {

    val TAG = "VolvoWeatherActivity"

    var cityNameList: ArrayList<String> = ArrayList()
    var cityCodeList: ArrayList<Int> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.lifecycleOwner = this
        val model: VolvoWeatherViewModel =
            ViewModelProvider(this).get(VolvoWeatherViewModel::class.java)
        binding.viewModel = model

        linearLayoutManager = LinearLayoutManager(this)
        binding.weatherList.layoutManager = linearLayoutManager

        binding.progressCircular.visibility = View.VISIBLE

        // adding list of cities for which we want weather report
        cityNameList.add("Gothenburg")
        cityNameList.add("Stockholm")
        cityNameList.add("Mountain View")
        cityNameList.add("London")
        cityNameList.add("New York")
        cityNameList.add("Berlin")

        // First we will fetch location code for all cities and after that we will fetch
        // weather report for all cities
        // Alternatively, we could have fetched location code and after that weather report for 1 city
        // and after updatng in recylerview will go for another city and call notifyDataSetChanged
        // in this way user screen will keep popping up with each city data after few milli seconds
        // But approach follwed in below code is we will fetch al data and show in recylerview at one go
        // since we have only few cities weather to show. If cities would have been much more we would have gone
        // sequentially and update recylerview

        model.fetchLocationData(cityNameList)

        model.locationCodes.observe(this, Observer {
            for (i in 0 until it.size) {
                Log.d(TAG, it[i].title)
                cityCodeList.add(it[i].woeid)
            }
            // now we have lovcation code for all cities, so we will fetch weather report
            model.fetchWeatherReport(cityCodeList)
        })

        model.weatherData.observe(this, Observer {
            // we have weather report for all cities, now we will show in recyler view
            adapter = WeatherAdapter(this, it)
            binding.weatherList.adapter = adapter
            binding.progressCircular.visibility = View.GONE
        })

        model.serverError.observe(this, Observer {
            if (it) {
                // there was some error in api
                Toast.makeText(this, getString(R.string.server_error), Toast.LENGTH_LONG)
            }
        })
    }
}