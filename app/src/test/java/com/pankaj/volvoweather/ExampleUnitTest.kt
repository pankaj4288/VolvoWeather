package com.pankaj.volvoweather

import com.pankaj.volvoweather.network.ApiInterface
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun fetchocationCode() {
        // api testing to see if api is showing correct result or not
        assertEquals(44418, ApiInterface.create().getLocationCodes("london").execute().body()?.get(0)?.woeid)
        assertEquals(890869, ApiInterface.create().getLocationCodes("Gothenburg").execute().body()?.get(0)?.woeid)
        assertEquals(906057, ApiInterface.create().getLocationCodes("Stockholm").execute().body()?.get(0)?.woeid)
        assertEquals(2455920, ApiInterface.create().getLocationCodes("Mountain View").execute().body()?.get(0)?.woeid)
        assertEquals(2459115, ApiInterface.create().getLocationCodes("New York").execute().body()?.get(0)?.woeid)
        assertEquals(638242, ApiInterface.create().getLocationCodes("Berlin").execute().body()?.get(0)?.woeid)
    }
}