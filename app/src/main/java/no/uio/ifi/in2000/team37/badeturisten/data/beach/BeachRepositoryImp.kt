package no.uio.ifi.in2000.team37.badeturisten.data.beach

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
    private val waterTempDataSource: WaterTemperatureDataSource
): BeachRepository {

    suspend fun waterTempGetData(): List<Tsery> {
        return waterTempDataSource.getData(59.91, 10.74, 10, 50)
    }

    //flows
    private val beachObservations = MutableStateFlow<List<Beach>>(listOf())
    private val favouriteObservations = MutableStateFlow<List<Beach>>(listOf())
    var beachlist: MutableList<Beach> = mutableListOf<Beach>()

    //henter flows
    override fun getBeachObservations(): StateFlow<List<Beach>> = beachObservations.asStateFlow()
    override fun getFavouriteObservations(): StateFlow<List<Beach>> =
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
                    tsery.header.extra.pos, tsery.observations.last().body.value.toDoubleOrNull()
                )
            }
        } catch (e: Exception) {
            Log.e("BeachRepository", e.message.toString())
            listOf()
        }
    }


    override suspend fun getBeach(beachName: String): Beach? =
        beachObservations.value.firstOrNull { beach -> beach.name == beachName }

    override fun updateFavourites(beach: Beach?) {
        if (beach != null) {
            if (beach in beachlist) {
                beachlist.remove(beach)
            } else {
                beachlist.add(beach)
            }
        }
        Log.d("beachrepo updFav", "$beachlist")
        favouriteObservations.update {
            beachlist
        }
    }
}