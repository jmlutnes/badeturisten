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
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.BusStations
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurGeocoderRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurJourneyPlannerRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.beach.OsloKommuneBeachInfo
import javax.inject.Inject

data class BeachUIState(
    val beach: Beach? = null,
    val beachInfo: OsloKommuneBeachInfo?,
    val busRoutes: MutableList<BusRoute> = mutableListOf(),
)

data class BusRoute(val routeNumber: String, val name: String, val transportMode: String)

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
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
                null,
                null,
                null
            ),
            busRoutes = mutableListOf()
        )
    )
    val beachUIState: StateFlow<BeachUIState> = _beachUIState.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isFavorited = MutableStateFlow(false)
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
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            val beachinfo: Beach? = _beachRepository.getBeach(beachName)
            val osloKommuneBeachInfo: Beach? = _osloKommuneRepository.getBeach(beachName)
            val lon = beachinfo?.pos?.lon?.toDouble()
            val lat = beachinfo?.pos?.lat?.toDouble()

            val busStations: BusStations? = if ((lon == null) || (lat == null)) {
                //Fetch ID for all buss stations based on name
                _enTurGeocoderRepository.hentBussruteName(beachName)
            } else {
                //Fetch ID for all buss stations based on location
                _enTurGeocoderRepository.hentBussruteLoc(lat, lon)
            }
            // The EnTurGeocoderRepository-methods for getting bus routes
            // only return null when there is an error, and will
            // return an empty object if there simply are no routes near the beach.
            // busStations being null is therefore indicative of an error
            if (busStations == null) {
                _isConnectivityIssue.update { true }
            }
            Log.d("beachViewModel", busStations.toString())

            val uniqueRoutes = mutableSetOf<BusRoute>()
            busStations?.stations?.forEach { station ->
                station.id?.let { id ->
                    _enTurJourneyPlannerRepository.getBusRoutesById(id)?.let { routes ->
                        uniqueRoutes.addAll(routes)
                    }
                }
            }
            val allRoutes: MutableList<BusRoute> = uniqueRoutes.toMutableList()
            val waterQuality: OsloKommuneBeachInfo? = _osloKommuneRepository.findWebPage(beachName)

            _beachUIState.update { currentUIState ->
                if (beachinfo != null) {
                    currentUIState.copy(
                        beach = beachinfo,
                        beachInfo = waterQuality,
                        busRoutes = allRoutes
                    )
                } else {
                    currentUIState.copy(
                        beach = osloKommuneBeachInfo,
                        beachInfo = waterQuality,
                        busRoutes = allRoutes
                    )
                }
            }
            if (beachinfo != null) {
                checkFavourite(beachinfo)
            } else if (osloKommuneBeachInfo != null) {
                checkFavourite(osloKommuneBeachInfo)
            } else {
                _isConnectivityIssue.update { true }
            }
            _isLoading.value = false
        }
    }
}