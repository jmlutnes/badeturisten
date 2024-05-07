package no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.Bussstasjoner
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurGeocoderRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurJourneyPlannerRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.beach.OsloKommuneBeachInfo
import javax.inject.Inject

data class BeachUIState(
    val beach: Beach? = null,
    val badevannsinfo: OsloKommuneBeachInfo?,
    val kollektivRute: MutableList<Bussrute> = mutableListOf(),
)

data class Bussrute(val linje: String, val navn: String, val transportMode: String)

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class BeachViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val _osloKommuneRepository: OsloKommuneRepository,
    private val _beachRepository: BeachRepository,
    private val _enTurRepositoryGeocoderRepository: EnTurGeocoderRepository,
    private val _enTurRepositoryJourneyPlanner: EnTurJourneyPlannerRepository,
) : ViewModel() {
    private val beachName: String = checkNotNull(savedStateHandle["beachName"])
    private val _beachUIState = MutableStateFlow(
        BeachUIState(
            null, OsloKommuneBeachInfo(
                null,
                null,
                null
            ),
            kollektivRute = mutableListOf<Bussrute>()
        )
    )
    val beachUIState: StateFlow<BeachUIState> = _beachUIState.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    init {
        loadBeachInfo()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadBeachInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            val beachinfo = _beachRepository.getBeach(beachName)
            val osloKommuneBeachInfo = _osloKommuneRepository.getBeach(beachName)
            val lon = beachinfo?.pos?.lon?.toDouble()
            val lat = beachinfo?.pos?.lat?.toDouble()
            println("lon:$lon \nlat:$lat")
            var bussstasjoner: Bussstasjoner? = null
            if ((lon == null) || (lat == null)) {
                //Fetch ID for all buss stations based on name
                bussstasjoner = _enTurRepositoryGeocoderRepository.hentBussruteName(beachName)
            } else {
                //Fetch ID for all buss stasions based on location
                bussstasjoner = _enTurRepositoryGeocoderRepository.hentBussruteLoc(lat, lon)
            }

            val unikeBussruter = mutableSetOf<Bussrute>()
            bussstasjoner?.bussstasjon?.forEach { stasjon ->
                stasjon.id?.let { id ->
                    _enTurRepositoryJourneyPlanner.hentBussruterMedId(id)?.let { bussruter ->
                        unikeBussruter.addAll(bussruter)
                    }
                }
            }
            val alleBussruter: MutableList<Bussrute> = unikeBussruter.toMutableList()
            val vannkvalitet: OsloKommuneBeachInfo? = _osloKommuneRepository.findWebPage(beachName)
            _beachUIState.update { currentUIState ->
                if (beachinfo != null) {
                    currentUIState.copy(
                        beach = beachinfo,
                        badevannsinfo = vannkvalitet,
                        alleBussruter
                    )
                } else {
                    currentUIState.copy(
                        beach = osloKommuneBeachInfo,
                        badevannsinfo = vannkvalitet,
                        alleBussruter
                    )
                }
            }
            _isLoading.value = false
        }
    }

    init {
        Log.d("ViewModelInit", "BeachViewModel using repository: $_beachRepository")
    }

    fun updateFavourites(beach: Beach) {
        _beachRepository.updateFavourites(beach)
    }
}