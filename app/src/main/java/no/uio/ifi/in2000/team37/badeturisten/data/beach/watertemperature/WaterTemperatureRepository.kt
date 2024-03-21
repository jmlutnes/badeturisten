package no.uio.ifi.in2000.team37.badeturisten.data.beach.watertemperature

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.data.beach.watertemperature.jsontokotlin.Tsery
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

class WaterTemperatureRepository{
    private val dataSource: WaterTemperatureDataSource = WaterTemperatureDataSource()
    suspend fun getData(): List<Tsery> {
        return dataSource.getData(59.91, 10.74, 10, 50)
    }
}