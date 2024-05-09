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

    private val _isFavorited = MutableStateFlow<Boolean>(false)
    val isFavorited: StateFlow<Boolean> = _isFavorited.asStateFlow()

    private val _isConnectivityIssue = MutableStateFlow(false)
    val isConnectivityIssue = _isConnectivityIssue.asStateFlow()

    fun checkAndUpdateFavorites(beach: Beach) {
        viewModelScope.launch {
            _beachRepository.updateFavourites(beach)
            _isFavorited.value = _beachRepository.checkFavourite(beach)
        }
    }

    fun checkFavourite(beach: Beach) {
        _isFavorited.value = _beachRepository.checkFavourite(beach)
        Log.d("beachviewmodel, checkFavourite", "Favorittstatus endret: ${_isFavorited.value}")
    }

    init {
        loadBeachInfo()
        Log.d("ViewModelInit", "BeachViewModel using repository: $_beachRepository")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadBeachInfo() {
        Log.d("debug", "start")
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            val beachinfo: Beach? = _beachRepository.getBeach(beachName)
            val osloKommuneBeachInfo: Beach? = _osloKommuneRepository.getBeach(beachName)
            val lon = beachinfo?.pos?.lon?.toDouble()
            val lat = beachinfo?.pos?.lat?.toDouble()

            var bussstasjoner: Bussstasjoner? = null
            bussstasjoner = if ((lon == null) || (lat == null)) {
                //Fetch ID for all buss stations based on name
                _enTurRepositoryGeocoderRepository.hentBussruteName(beachName)
            } else {
                //Fetch ID for all buss stasions based on location
                _enTurRepositoryGeocoderRepository.hentBussruteLoc(lat, lon)
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
            if (beachinfo != null) {
                _isConnectivityIssue.update { false }
                checkFavourite(beachinfo)
            } else if (osloKommuneBeachInfo != null) {
                _isConnectivityIssue.update { false }
                checkFavourite(osloKommuneBeachInfo)
            } else {
                _isConnectivityIssue.update { true }
            }
            _isLoading.value = false
        }
    }
}