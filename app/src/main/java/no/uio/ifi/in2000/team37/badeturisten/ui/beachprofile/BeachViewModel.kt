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
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.Busstations
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurGeocoderRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurJourneyPlannerRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.beach.OsloKommuneBeachInfo
import no.uio.ifi.in2000.team37.badeturisten.model.enTur.Busstation
import javax.inject.Inject

data class BeachUIState(
    val beach: Beach? = null,
    val beachInfo: OsloKommuneBeachInfo?,
    val transportationRoutes: MutableList<BusRoute> = mutableListOf(),
)

data class BusRoute(
    val line: String,
    val name: String,
    val transportMode: String,
    val busstation: Busstation,
)

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class BeachViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val _osloKommuneRepository: OsloKommuneRepository,
    private val _beachRepository: BeachRepository,
    private val _enTurRepositoryGeocoderRepository: EnTurGeocoderRepository,
    private val _enTurRepositoryJourneyPlanner: EnTurJourneyPlannerRepository,
) : ViewModel() {
    private val beachName: String = checkNotNull(savedStateHandle["beachName"])

    private val _beachUIState = MutableStateFlow(
        BeachUIState(
            null, OsloKommuneBeachInfo(
                null, null, null
            ), transportationRoutes = mutableListOf()
        )
    )
    val beachUIState: StateFlow<BeachUIState> = _beachUIState.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isFavorited = MutableStateFlow(false)
    val isFavorited: StateFlow<Boolean> = _isFavorited.asStateFlow()

    /**
     * Update favourite list
     */
    fun checkAndUpdateFavorites(beach: Beach) {
        viewModelScope.launch {
            _beachRepository.updateFavourites(beach)
            _isFavorited.value = _beachRepository.checkFavourite(beach)
        }
    }

    /**
     * Check if beach is in favourite list
     */
    fun checkFavourite(beach: Beach) {
        _isFavorited.value = _beachRepository.checkFavourite(beach)
        Log.d("beachviewmodel, checkFavourite", "Favorittstatus changed: ${_isFavorited.value}")
    }

    init {
        loadBeachInfo()
        Log.d("ViewModelInit", "BeachViewModel using repository: $_beachRepository")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadBeachInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            val beachinfo: Beach? = _beachRepository.getBeach(beachName)
            val osloKommuneBeachInfo: Beach? = _osloKommuneRepository.getBeach(beachName)
            val lon = beachinfo?.pos?.lon?.toDouble()
            val lat = beachinfo?.pos?.lat?.toDouble()

            val busstations: Busstations? = if ((lon == null) || (lat == null)) {
                //Fetch ID for all buss stations based on name
                _enTurRepositoryGeocoderRepository.fetchBusRouteName(beachName)
            } else {
                //Fetch ID for all buss stasions based on location
                _enTurRepositoryGeocoderRepository.fetchBusRouteLoc(lat, lon)
            }

            val uniqueBusRoutes = mutableSetOf<BusRoute>()
            busstations?.busstation?.forEach { station ->
                station.id?.let { id ->
                    _enTurRepositoryJourneyPlanner.fetchBusroutesById(id, station)
                        ?.let { busroutes ->
                            uniqueBusRoutes.addAll(busroutes)
                        }
                }
            }
            val allBusRoutes: MutableList<BusRoute> = uniqueBusRoutes.toMutableList()
            val waterQuality: OsloKommuneBeachInfo? = _osloKommuneRepository.findWebPage(beachName)
            _beachUIState.update { currentUIState ->
                if (beachinfo != null) {
                    currentUIState.copy(
                        beach = beachinfo,
                        beachInfo = waterQuality,
                        transportationRoutes = allBusRoutes
                    )
                } else {
                    currentUIState.copy(
                        beach = osloKommuneBeachInfo,
                        beachInfo = waterQuality,
                        transportationRoutes = allBusRoutes
                    )
                }
            }
            if (beachinfo != null) {
                checkFavourite(beachinfo)
            } else if (osloKommuneBeachInfo != null) {
                checkFavourite(osloKommuneBeachInfo)
            }
            _isLoading.value = false
        }
    }
}