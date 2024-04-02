package no.uio.ifi.in2000.team37.badeturisten.data.beach

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Tsery
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BadevannsInfo
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

class BeachRepository {
    //henter fra oslo kommune repository
    val osloKommuneRepository: OsloKommuneRepository = OsloKommuneRepository()

    //henter fra watertemperature data source
    private val waterTempDataSource: WaterTemperatureDataSource = WaterTemperatureDataSource()

    suspend fun waterTempGetData(): List<Tsery> {
        return waterTempDataSource.getData(59.91, 10.74, 10, 50)
    }
    private val beachObservations = MutableStateFlow<List<Beach>>(listOf())

    fun getBeachObservations() = beachObservations.asStateFlow()

    suspend fun loadBeaches() {
        val observationsFromDataSource = waterTempGetData()

        //oppdaterer i homeviewmodel i stedet
        beachObservations.update {
            makeBeaches(observationsFromDataSource)
        }
    }

    //Kan vi lagre geometri ogsaa
    suspend fun makeBeaches(data: List<Tsery>): List<Beach> {
        return try {
            //gjoer data om til liste med strender
            val liste: MutableList<Beach> = mutableListOf<Beach>()
            data.forEach { data ->
                val beachName = data.header.extra.name
                // oppretter strand objekter og legger til i liste
                val waterTemperature = data.observations.first().body.value.toDoubleOrNull() ?: 0.0
                val position = data.header.extra.pos
                if (position.lat.toDouble() != null && position.lon.toDouble() != null) {
                    //val vannkvalitet: BadevannInfo? = osloKommuneRepository.getWaterQuality(position.lat.toDouble(), position.lon.toDouble())
                }

                liste.add(Beach(beachName, position, waterTemperature))
            }

            return liste
        } catch (e: Exception) {
            Log.d("beach repository", "failed to make beaches")
            Log.e("beach repository", e.message.toString())
            emptyList<Beach>()
        }
    }

    suspend fun getBeach(beachName: String): Beach? {
        val observationsFromDataSource = waterTempGetData()
        var beachlist: List<Beach> = makeBeaches(observationsFromDataSource)
        beachlist = beachlist.filter { beach -> beach.name == beachName }
        return beachlist.firstOrNull()
    }

    suspend fun getWaterQuality(lat: Double, lon: Double): BadevannsInfo? {
        return osloKommuneRepository.getVannkvalitet(lat = lat, lon = lon)
    }
}