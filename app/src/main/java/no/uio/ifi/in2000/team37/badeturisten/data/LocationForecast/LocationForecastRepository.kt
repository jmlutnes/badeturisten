package no.uio.ifi.in2000.team37.badeturisten.data.LocationForecast

import android.util.Log
import no.uio.ifi.in2000.team37.badeturisten.data.LocationForecast.JsonToKotlinLocationForecast.LocationForecastData

class LocationForecastRepository(val dataSource: LocationForecastDataSource) {
    suspend fun getTemperature(): Double?{
        val result = dataSource.getForecastData()
        val temp = result.properties.timeseries.firstOrNull()?.data?.instant?.details?.airTemperature
        Log.v("print", "$result")
        return temp
    }
}