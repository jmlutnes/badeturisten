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
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurRepository
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.EnTurJourneyPlannerDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.EnTurJourneyPlannerRepository
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BadevannsInfo
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.ui.search.SokKommuneBeachList

data class BeachUIState(val beach: Beach? = null, val badevannsinfo: BadevannsInfo?, val kollektivRute: MutableList<Bussrute> = mutableListOf())
data class Bussrute(val linje: String, val navn: String)

@RequiresApi(Build.VERSION_CODES.O)
class BeachViewModel(savedStateHandle : SavedStateHandle): ViewModel() {
    private val beachName: String = checkNotNull(savedStateHandle["beachName"])
    private val beachRepository: BeachRepository = BeachRepository()

    private val _beachRepository: BeachRepository = BeachRepository()
    private val _beachUIState = MutableStateFlow(BeachUIState(
        null,
        BadevannsInfo("", "", "", ""),
        kollektivRute = mutableListOf<Bussrute>()
    ))
    val beachUIState: StateFlow<BeachUIState> = _beachUIState.asStateFlow()


    private val osloKommuneRepository: OsloKommuneRepository = OsloKommuneRepository()
    private val enTurRepository: EnTurRepository = EnTurRepository(EnTurDataSource())
    private val enTurRepositoryJourneyPlanner: EnTurJourneyPlannerRepository = EnTurJourneyPlannerRepository(
        EnTurJourneyPlannerDataSource()
    )

    init { loadBeachInfo() }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadBeachInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val beachinfo = beachRepository.getBeach(beachName)
            val osloKommuneBeachInfo = osloKommuneRepository.getBeach(beachName)
            val lon = beachinfo?.pos?.lat?.toDouble()
            val lat = beachinfo?.pos?.lon?.toDouble()
            println("lon:$lon \nlat:$lat")
            var flereruter: MutableList<MutableList<Bussrute>>
            //Hent kollektivrute
            val bussstasjoner = enTurRepository.hentBussrute(beachName)

            // Bruker et Set for å unngå duplikater
            val unikeBussruter = mutableSetOf<Bussrute>()
            bussstasjoner?.bussstasjon?.forEach { stasjon ->
                stasjon.id?.let { id ->
                    enTurRepositoryJourneyPlanner.hentBussruterMedId(id)?.let { bussruter ->
                        unikeBussruter.addAll(bussruter)
                    }
                }
            }

            // Konverterer Set til MutableList hvis det er nødvendig for UI-logikken
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
