package no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.OsloKommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BadevannInfo
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

data class BeachUIState(val beach: Beach? = null, val badevannsinfo: BadevannInfo?)
//data class UiKommune(val badevannsinfo: BadevannsInfo?)

//Viewmodel som henter strom fra kun en strand
class BeachViewModel(savedStateHandle : SavedStateHandle): ViewModel() {
    private val _beachName : String = checkNotNull(savedStateHandle["beachName"])

    private val _beachRepository: BeachRepository = BeachRepository()
    private val _beachUIState = MutableStateFlow(BeachUIState(null, BadevannInfo("", "", null)))
    val beachUIState: StateFlow<BeachUIState> = _beachUIState.asStateFlow()

    private val osloKommuneRepository = OsloKommuneRepository()

    init {
        viewModelScope.launch {
            val beachinfo = _beachRepository.getBeach(_beachName)

            val lon = beachinfo?.pos?.lat?.toDouble()
            val lat = beachinfo?.pos?.lon?.toDouble()

            if (lat != null && lon != null) {
                val vannkvalitet: BadevannInfo? = osloKommuneRepository.getWaterQuality(lat, lon)
                _beachUIState.update { currentUIState ->
                    currentUIState.copy(beach = beachinfo, badevannsinfo = vannkvalitet)
                }
            } else {
                println("Ingen gyldig posisjon funnet for stranden.")
            }
        }
    }

}