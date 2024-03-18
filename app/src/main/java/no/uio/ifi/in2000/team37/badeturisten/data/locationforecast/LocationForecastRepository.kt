package no.uio.ifi.in2000.team37.badeturisten.data.locationforecast

import android.util.Log

class LocationForecastRepository(val dataSource: LocationForecastDataSource) {
    suspend fun getTemperature(): Double?{
        val result = dataSource.getForecastData()
        val temp = result.properties.timeseries.firstOrNull()?.data?.instant?.details?.airTemperature
        Log.v("print", "$result")
        return temp
    }
}