package no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile

import android.util.Log
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
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.BusStations
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurGeocoderRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurJourneyPlannerRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.beach.OsloKommuneBeachInfo
import no.uio.ifi.in2000.team37.badeturisten.model.enTur.BusStation
import javax.inject.Inject

data class BeachUIState(
    val beach: Beach? = null,
    val beachInfo: OsloKommuneBeachInfo?,
    val transportationRoutes: MutableList<BusRoute> = mutableListOf(),
)

data class BusRoute(
    val line: String?,
    val name: String,
    val transportMode: String?,
    val busStation: BusStation,
)

@HiltViewModel
class BeachViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val _osloKommuneRepository: OsloKommuneRepository,
    private val _beachRepository: BeachRepository,
    private val _enTurGeocoderRepository: EnTurGeocoderRepository,
    private val _enTurJourneyPlannerRepository: EnTurJourneyPlannerRepository,
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

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private val _isConnectivityIssue = MutableStateFlow(false)
    val isConnectivityIssue = _isConnectivityIssue.asStateFlow()

    /**
     * Update favorite list
     */
    fun checkAndUpdateFavorites(beach: Beach) {
        viewModelScope.launch {
            _beachRepository.updateFavorites(beach)
            _isFavorite.value = _beachRepository.checkFavorite(beach)
        }
    }

    /**
     * Check if beach is in favorite list
     */
    fun checkFavorite(beach: Beach) {
        _isFavorite.value = _beachRepository.checkFavorite(beach)
        Log.d("beachviewmodel, checkFavorite", "Favorite-status changed: ${_isFavorite.value}")
    }

    init {
        loadBeachInfo()
        Log.d("ViewModelInit", "BeachViewModel using repository: $_beachRepository")
    }

    private fun loadBeachInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            val beachInfo: Beach? = _beachRepository.getBeach(beachName)
            val osloKommuneBeachInfo: Beach? = _osloKommuneRepository.getBeach(beachName)
            val lon = beachInfo?.pos?.lon
            val lat = beachInfo?.pos?.lat
            var busStations: BusStations?


            if ((lon == null) || (lat == null)) {
                //Fetch ID for all buss stations based on name
                busStations = _enTurGeocoderRepository.fetchBusRouteName(beachName)
            } else {
                //Fetch ID for all buss stations based on location
                busStations = _enTurGeocoderRepository.fetchBusRouteLoc(
                    lat.toDouble(), lon.toDouble()
                )
                if (busStations?.busStation?.isEmpty() == true) {
                    busStations = _enTurGeocoderRepository.fetchBusRouteName(beachName)
                }
            }
            // The EnTurGeocoderRepository-methods for getting bus routes
            // only return null when there is an error, and will
            // return an empty object if there simply are no routes near the beach.
            // busStations being null is therefore indicative of an error
            if (busStations == null) {
                Log.d("snackbar", "$busStations")
                _isConnectivityIssue.update { true }
            }
            val uniqueBusRoutes = mutableSetOf<BusRoute>()
            busStations?.busStation?.forEach { station ->
                station.id?.let { id ->
                    _enTurJourneyPlannerRepository.fetchBusroutesById(id, station)
                        ?.let { busRoutes ->
                            uniqueBusRoutes.addAll(busRoutes)
                        }
                }
            }
            val allBusRoutes: MutableList<BusRoute> = uniqueBusRoutes.toMutableList()
            val waterQuality: OsloKommuneBeachInfo? = _osloKommuneRepository.findWebPage(beachName)

            _beachUIState.update { currentUIState ->
                if (beachInfo != null) {
                    currentUIState.copy(
                        beach = beachInfo,
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
            if (beachInfo != null) {
                checkFavorite(beachInfo)
            } else if (osloKommuneBeachInfo != null) {
                checkFavorite(osloKommuneBeachInfo)
            } else {
                _isConnectivityIssue.update { true }
            }
            _isLoading.value = false
        }
    }
}