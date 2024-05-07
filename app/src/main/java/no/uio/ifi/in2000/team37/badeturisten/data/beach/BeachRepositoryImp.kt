package no.uio.ifi.in2000.team37.badeturisten.data.beach

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Tsery
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class BeachRepositoryImp @Inject constructor(
    private val waterTempDataSource: WaterTemperatureDataSource,
    private val beachListDataStore: DataStore<List<Beach>>,
    private val coroutineScope: CoroutineScope
) : BeachRepository {
    init {
        // loadInitialData
        coroutineScope.launch {
            beachListDataStore.data.collect { beaches ->
                favouriteList = beaches.toMutableList()
                favouriteObservations.value = favouriteList
            }
        }
    }

    private val beachObservations = MutableStateFlow<List<Beach>>(listOf())
    private val favouriteObservations = MutableStateFlow<List<Beach>>(listOf())
    var favouriteList: MutableList<Beach> = mutableListOf<Beach>()

    override fun getBeachObservations(): StateFlow<List<Beach>> = beachObservations.asStateFlow()
    override fun getFavouriteObservations(): StateFlow<List<Beach>> =
        favouriteObservations.asStateFlow()

    override suspend fun waterTempGetData(): List<Tsery> {
        return waterTempDataSource.getData(59.91, 10.74, 10, 50)
    }

    override suspend fun loadBeaches() {
        val observationsFromDataSource = waterTempGetData()

        //oppdaterer i homeviewmodel i stedet
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

    // favourites only work on beach objects
    override suspend fun updateFavourites(beach: Beach?): List<Beach>{
        if (beach != null) {
            if (beach in favouriteList) {
                favouriteList.remove(beach)
            } else {
                favouriteList.add(beach)
            }

            favouriteObservations.value = favouriteList  // Make sure this line is executing
            Log.d("BeachRepository", "Favorites updated: $favouriteList")

            try {
                beachListDataStore.updateData {favouriteList.toList()  // Convert mutable list to list
                }
                val results: List<Beach> = beachListDataStore.data.first()
                Log.d("BeachRepository", "Successfully updated and read from DataStore: $results")
            } catch (e: Exception) {
                Log.e("BeachRepository", "Failed to update DataStore with favouriteList", e)
            }
        }
        return favouriteList
    }
}