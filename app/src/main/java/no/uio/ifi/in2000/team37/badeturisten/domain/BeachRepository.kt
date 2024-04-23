package no.uio.ifi.in2000.team37.badeturisten.domain

import kotlinx.coroutines.flow.StateFlow
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Tsery
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

interface BeachRepository {
    //val waterTempDataSource: WaterTemperatureDataSource
    fun getBeachObservations(): StateFlow<List<Beach>>
    fun getFavouriteObservations(): StateFlow<List<Beach>>
    suspend fun loadBeaches()
    fun makeBeaches(observations: List<Tsery>): List<Beach>
    suspend fun getBeach(beachName: String): Beach?
    fun updateFavourites(beach: Beach?)
}