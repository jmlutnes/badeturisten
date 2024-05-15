package no.uio.ifi.in2000.team37.badeturisten.data.beach

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Tsery
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import javax.inject.Inject

class BeachRepositoryImp @Inject constructor(
    private val waterTempDataSource: WaterTemperatureDataSource,
    private val beachListDataStore: DataStore<List<Beach>>,
    coroutineScope: CoroutineScope,
) : BeachRepository {
    init {
        // loadInitialData
        coroutineScope.launch {
            beachListDataStore.data.collect { beaches ->
                favoriteList = beaches.toMutableList()
                favoriteObservations.value = favoriteList
            }
        }
    }

    private val beachObservations = MutableStateFlow<List<Beach>>(listOf())
    private val favoriteObservations = MutableStateFlow<List<Beach>>(listOf())
    private var favoriteList: MutableList<Beach> = mutableListOf()

    override fun getBeachObservations(): StateFlow<List<Beach>> = beachObservations.asStateFlow()
    override fun getFavoriteObservations(): StateFlow<List<Beach>> =
        favoriteObservations.asStateFlow()

    override suspend fun waterTempGetData(): List<Tsery> {
        return waterTempDataSource.getData(7)
    }

    override suspend fun loadBeaches() {
        val observationsFromDataSource = waterTempGetData()

        beachObservations.update {
            makeBeaches(observationsFromDataSource)
        }
    }

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


    override suspend fun getBeach(beachName: String): Beach? =
        beachObservations.value.firstOrNull { beach -> beach.name == beachName }

    override suspend fun updateFavorites(beach: Beach?): List<Beach> {
        if (beach != null) {
            if (beach in favoriteList) {
                favoriteList.remove(beach)
            } else {
                favoriteList.add(beach)
            }

            favoriteObservations.value = favoriteList  // Make sure this line is executing
            Log.d("BeachRepository", "Favorites updated: $favoriteList")

            try {
                beachListDataStore.updateData {
                    favoriteList.toList()  // Convert mutable list to list
                }
                val results: List<Beach> = beachListDataStore.data.first()
                Log.d("BeachRepository", "Successfully updated and read from DataStore: $results")
            } catch (e: Exception) {
                Log.e("BeachRepository", "Failed to update DataStore with favoriteList", e)
            }
        }
        return favoriteList
    }

    override fun checkFavorite(beach: Beach): Boolean {
        return beach in favoriteList
    }
}