package no.uio.ifi.in2000.team37.badeturisten.data.beach

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.data.OsloKommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureRepository
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Tsery
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

class BeachRepository {
    private val beachObservations = MutableStateFlow<List<Beach>>(listOf())
    val waterTempRepository : WaterTemperatureRepository = WaterTemperatureRepository()
    val osloKommuneRepository: OsloKommuneRepository = OsloKommuneRepository()

    fun getBeachObservations() = beachObservations.asStateFlow()

    suspend fun loadBeaches() {
        val observationsFromDataSource = waterTempRepository.getData()

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
        val observationsFromDataSource = waterTempRepository.getData()
        var beachlist: List<Beach> = makeBeaches(observationsFromDataSource)
        beachlist = beachlist.filter { beach -> beach.name == beachName }
        return beachlist.firstOrNull()
    }

    suspend fun getVannkvalitetLoc(lat: Double?, lon: Double?): no.uio.ifi.in2000.team37.badeturisten.data.OsloKommune.BadevannsInfo? {
        val nettsideUrl: String? =
            osloKommuneRepository.getClass(lat, lon).data.geoJson.features.firstOrNull()?.properties?.popupContent
        println("old: $nettsideUrl")
        val nynettsideUrl = nettsideUrl?.let { osloKommuneRepository.extractUrl(it) }
        if (nynettsideUrl != null) {
            println("Ekstrahert URL: $nynettsideUrl")
        } else {
            println("Ingen URL funnet.")
        }
        val skrapOsloKommune = nynettsideUrl?.let { osloKommuneRepository.skrapUrl(it) }
        //println("Skrapt innhold: $skrapOsloKommune")
        return skrapOsloKommune
    }
}