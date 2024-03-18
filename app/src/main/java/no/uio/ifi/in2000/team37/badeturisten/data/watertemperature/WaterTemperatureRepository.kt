package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Tsery
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

class WaterTemperatureRepository (val dataSource: WaterTemperatureDataSource){

    private val observations = MutableStateFlow<List<Beach>>(listOf())

    fun getObservations() = observations.asStateFlow()


    suspend fun loadBeaches() {
        val observationsFromDataSource = dataSource.getData(59.91, 10.74, 10, 50)

        //oppdaterer i homeviewmodel i stedet
        observations.update {
            makeBeaches(observationsFromDataSource)
        }
    }

    suspend fun makeBeaches(data: List<Tsery>): List<Beach> {
        return try {
            //gjoer data om til liste med strender
            val liste: MutableList<Beach> = mutableListOf<Beach>()

            data.forEach { data ->
                // lager strand objekter og legger til i liste
                liste.add(Beach(data.header.extra.name, ))
            }

            return liste
        } catch (e: Exception) {
            Log.d("water repository", "failed to make beaches")
            Log.e("water repos", e.message.toString())
            emptyList<Beach>()
        }
    }

    suspend fun getBeach(beachName: String): Beach? {
        val observationsFromDataSource = dataSource.getData(59.91, 10.74, 10, 50)
        var beachlist: List<Beach> = makeBeaches(observationsFromDataSource)
        beachlist = beachlist.filter { beach -> beach.name == beachName }
        return beachlist.firstOrNull()
    }
}