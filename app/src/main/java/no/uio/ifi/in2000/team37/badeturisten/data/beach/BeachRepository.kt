package no.uio.ifi.in2000.team37.badeturisten.data.beach

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Tsery
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

class BeachRepository {
    private val waterTempDataSource: WaterTemperatureDataSource = WaterTemperatureDataSource()
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun waterTempGetData(): List<Tsery> {
        return waterTempDataSource.getData(59.91, 10.74, 10, 50)
    }

    private val beachObservations = MutableStateFlow<List<Beach>>(listOf())
    fun getBeachObservations() = beachObservations.asStateFlow()
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun loadBeaches() {
        val observationsFromDataSource = waterTempGetData()
        beachObservations.update {
            makeBeaches(observationsFromDataSource)
        }
    }

    fun makeBeaches(observations: List<Tsery>): List<Beach> {
        return try {
            observations.map { tsery ->
                Beach(tsery.header.extra.name,
                    tsery.header.extra.pos,tsery.observations.last().body.value.toDoubleOrNull(),
                    false)
            }
        } catch (e: Exception) {
            Log.e("BeachRepository", e.message.toString())
            listOf()
        }
    }

//Denne maa naa ha egen observation og beachlist siden hver beach
// ikke har observasjonene fra HomeScreen(?)
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getBeach(beachName: String): Beach? {
        val observationsFromDataSource = waterTempGetData()
        var beachlist: List<Beach> = makeBeaches(observationsFromDataSource)
        beachlist = beachlist.filter { beach -> beach.name == beachName }
        return beachlist.firstOrNull()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getFavourites(): List<Beach> {
        val observationsFromDataSource = waterTempGetData()
        var beachlist: List<Beach> = makeBeaches(observationsFromDataSource)
        beachlist = beachlist.filter { beach -> beach.favorite }
        return beachlist
    }
}