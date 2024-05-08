package no.uio.ifi.in2000.team37.badeturisten.domain

import kotlinx.coroutines.flow.StateFlow
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Tsery
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

interface BeachRepository {
    /**
     * Fetch information from WaterTemperatureDataSource
     */
    suspend fun waterTempGetData(): List<Tsery>

    /**
     * Fetch an information flow of all beaches
     */
    fun getBeachObservations(): StateFlow<List<Beach>>

    /**
     * Fetch an information flow of favourite beaches
     */
    fun getFavouriteObservations(): StateFlow<List<Beach>>

    /**
     * Updates flow that contains all beaches
     */
    suspend fun loadBeaches()

    /**
     * Receives a list of deserialized information from datasource and filters the data to make beach objects
     */
    fun makeBeaches(observations: List<Tsery>): List<Beach>

    /**
     * Recieves a beach name and filter all beaches based on that name.
     * Returns one beach og NULL if beach does not exists
     */
    suspend fun getBeach(beachName: String): Beach?

    /**
     * Recieves a beach object and adds or removes the beach based on the favourite beach list
     * Returns list of favourite beaches
     */
    suspend fun updateFavourites(beach: Beach?): List<Beach>

    /**
     * checks and return ture or false depending on if a beach is favourite list
     */
    fun checkFavourite(beach: Beach): Boolean
}