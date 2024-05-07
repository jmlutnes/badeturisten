package no.uio.ifi.in2000.team37.badeturisten.data.beach

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Tsery
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class BeachRepositoryImp @Inject constructor(
    private val waterTempDataSource: WaterTemperatureDataSource,
    private val beachListDataStore: DataStore<List<Beach>>
) : BeachRepository {

    override suspend fun waterTempGetData(): List<Tsery> {
        return waterTempDataSource.getData(59.91, 10.74, 10, 50)
    }

    //flows
    private val beachObservations = MutableStateFlow<List<Beach>>(listOf())
    private val favouriteObservations = MutableStateFlow<List<Beach>>(listOf())
    val beachlist: MutableList<Beach> = mutableListOf<Beach>()

    //henter flows
    override fun getBeachObservations() = beachObservations.asStateFlow()
    override fun getFavouriteObservations() =
        favouriteObservations.asStateFlow()

    //oppdaterer flows
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
            if (beach in beachlist) {
                beachlist.remove(beach)
            } else {
                beachlist.add(beach)
            }
        }
        favouriteObservations.value = beachlist  // Make sure this line is executing
        Log.d("BeachRepository", "Favorites updated: $beachlist")
        try {
            beachListDataStore.updateData { currentBeaches ->
                emptyList()  // Test with an empty list
            }
        } catch (e: Exception) {
            Log.e("BeachRepository", "Failed to update with empty list", e)
            throw e
        }
        /*try {
            beachListDataStore.updateData { currentBeaches ->
                Log.d("BeachRepository", "Current beaches in DataStore: $currentBeaches")
                beachlist.toList()  // Assuming this transformation is necessary
            }
        }catch (e: Exception) {
            Log.e("BeachRepository", "Failed to save beachs: $beachlist", e)
            throw e  // Or handle gracefully depending on your error strategy
        }*/
        return beachlist
    }
}