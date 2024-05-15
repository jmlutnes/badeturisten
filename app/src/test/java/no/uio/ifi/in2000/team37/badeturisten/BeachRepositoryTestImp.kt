package no.uio.ifi.in2000.team37.badeturisten

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Tsery
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

class BeachRepositoryTestImp: BeachRepository {

    private val beachObservations = MutableStateFlow<List<Beach>>(listOf())

    private val client = HttpClient {
        defaultRequest {
            url("https://havvarsel-frost.met.no")
        }
        install(ContentNegotiation) { gson {} }
        install(HttpTimeout) {
            this.requestTimeoutMillis = 5000
            this.connectTimeoutMillis = 5000
            this.socketTimeoutMillis = 5000
        }
    }
    private val waterTempDataSource = WaterTemperatureDataSource(client)

    override suspend fun loadBeaches() {
        val observationsFromDataSource = waterTempGetData()

        beachObservations.update {
            makeBeaches(observationsFromDataSource)
        }
    }

    override suspend fun getBeach(beachName: String): Beach? =
        beachObservations.value.firstOrNull { beach -> beach.name == beachName }

    override fun makeBeaches(observations: List<Tsery>): List<Beach> {
        return try {
            observations.map { tsery ->
                Beach(
                    tsery.header.extra.name,
                    tsery.header.extra.pos,
                    tsery.observations.last().body.value.toDoubleOrNull()
                )
            }
        } catch (e: Exception) {
            Log.e("BeachRepository", e.message.toString())
            listOf()
        }
    }

    override suspend fun updateFavorites(beach: Beach?): List<Beach> {
        TODO("Not yet implemented")
    }

    override fun checkFavorite(beach: Beach): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun waterTempGetData(): List<Tsery> {
        return waterTempDataSource.getData(7)
    }

    override fun getBeachObservations(): StateFlow<List<Beach>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteObservations(): StateFlow<List<Beach>> {
        TODO("Not yet implemented")
    }

}