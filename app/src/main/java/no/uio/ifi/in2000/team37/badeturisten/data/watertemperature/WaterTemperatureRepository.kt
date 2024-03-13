package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.model.watertemperature.Tsery

class WaterTemperatureRepository (val dataSource: WaterTemperatureDataSource){

    private val observations = MutableStateFlow<List<Tsery>>(listOf())

    fun getObservations() = observations.asStateFlow()

    suspend fun loadObservations() {
        val observationsFromDataSource = dataSource.getData(59.91, 10.74, 10, 50)

        observations.update {
            observationsFromDataSource
        }
    }
}