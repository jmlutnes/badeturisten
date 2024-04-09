package no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.Bussstasjoner
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderRepository
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.EnTurJourneyPlannerDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.EnTurJourneyPlannerRepository
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BadevannsInfo
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

data class BeachUIState(val beach: Beach? = null, val badevannsinfo: BadevannsInfo?, val kollektivRute: MutableList<Bussrute> = mutableListOf())
data class Bussrute(val linje: String, val navn: String, val transportMode: String)

@RequiresApi(Build.VERSION_CODES.O)
class BeachViewModel(savedStateHandle : SavedStateHandle): ViewModel() {
    //Beach
    private val beachName: String = checkNotNull(savedStateHandle["beachName"])
    private val beachRepository: BeachRepository = BeachRepository()

    private val _beachRepository: BeachRepository = BeachRepository()
    private val _beachUIState = MutableStateFlow(BeachUIState(
        null,
        BadevannsInfo("", "", "", ""),
        kollektivRute = mutableListOf<Bussrute>()
    ))
    val beachUIState: StateFlow<BeachUIState> = _beachUIState.asStateFlow()
    //Oslo Kommune
    private val osloKommuneRepository: OsloKommuneRepository = OsloKommuneRepository()
    //Ruter
    private val enTurRepositoryGeocoderRepository: EnTurGeocoderRepository = EnTurGeocoderRepository(EnTurGeocoderDataSource())
    private val enTurRepositoryJourneyPlanner: EnTurJourneyPlannerRepository = EnTurJourneyPlannerRepository(
        EnTurJourneyPlannerDataSource()
    )

    init { loadBeachInfo() }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadBeachInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val beachinfo = beachRepository.getBeach(beachName)
            val osloKommuneBeachInfo = osloKommuneRepository.getBeach(beachName)
            val lon = beachinfo?.pos?.lon?.toDouble()
            val lat = beachinfo?.pos?.lat?.toDouble()
            println("lon:$lon \nlat:$lat")
            var bussstasjoner: Bussstasjoner? = null
            if((lon == null) || (lat == null)) {
                //Henter ID for alle bussstasjoner som finner basert paa navn
                bussstasjoner = enTurRepositoryGeocoderRepository.hentBussruteName(beachName)
            }
            else{
                //Henter ID for alle busstasjoner som finner basert paa lokasjon
            bussstasjoner = enTurRepositoryGeocoderRepository.hentBussruteLoc(lat, lon)}
            //}
            // Henter bussruter (linje og navn) basert paa id fra stasjoner
            // Set for ingen duplikater
            val unikeBussruter = mutableSetOf<Bussrute>()
            bussstasjoner?.bussstasjon?.forEach { stasjon ->
                stasjon.id?.let { id ->
                    enTurRepositoryJourneyPlanner.hentBussruterMedId(id)?.let { bussruter ->
                        unikeBussruter.addAll(bussruter)
                    }
                }
            }
            val alleBussruter: MutableList<Bussrute> = unikeBussruter.toMutableList()
            val vannkvalitet: BadevannsInfo? = osloKommuneRepository.finnNettside(beachName)
            _beachUIState.update { currentUIState ->
                if (beachinfo != null) {
                    currentUIState.copy(beach = beachinfo, badevannsinfo = vannkvalitet, alleBussruter)
                } else {
                    currentUIState.copy(beach = osloKommuneBeachInfo, badevannsinfo = vannkvalitet, alleBussruter)
                }
            }

        }
    }
}
