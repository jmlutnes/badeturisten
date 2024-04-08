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
import no.uio.ifi.in2000.team37.badeturisten.data.entur.enturgeocode.EnTurDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.entur.enturjourneyplanner.EnTurJourneyPlannerDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.entur.enturjourneyplanner.EnTurJourneyPlannerRepository
import no.uio.ifi.in2000.team37.badeturisten.data.entur.enturgeocode.EnTurRepository
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BadevannsInfo
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

data class BeachUIState(val beach: Beach? = null, val badevannsinfo: BadevannsInfo?, val kollektivRute: MutableList<String>?)

@RequiresApi(Build.VERSION_CODES.O)
class BeachViewModel(savedStateHandle : SavedStateHandle): ViewModel() {
    private val beachName: String = checkNotNull(savedStateHandle["beachName"])
    private val beachRepository: BeachRepository = BeachRepository()

    private val _beachRepository: BeachRepository = BeachRepository()
    private val _beachUIState = MutableStateFlow(BeachUIState(
        null,
        BadevannsInfo("", "", "", ""),
        kollektivRute = null
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

            //Hent kollektivrute
            val bussstasjoner = enTurRepository.hentBussrute(beachName)
            val kollektivruter = mutableListOf<String>()

            bussstasjoner?.bussstasjon?.forEach { stasjon ->
                stasjon.id?.let {
                    val ruter = enTurRepositoryJourneyPlanner.hentBussruterMedId(it)
                    kollektivruter.add((ruter ?: "").toString())
                }
            }

            val vannkvalitet: BadevannsInfo? = osloKommuneRepository.finnNettside(beachName)
            _beachUIState.update { currentUIState ->
                if (beachinfo != null) {
                    currentUIState.copy(beach = beachinfo, badevannsinfo = vannkvalitet, kollektivruter)
                } else {
                    currentUIState.copy(beach = osloKommuneBeachInfo, badevannsinfo = vannkvalitet, kollektivruter)
                }
            }

        }
    }
}
