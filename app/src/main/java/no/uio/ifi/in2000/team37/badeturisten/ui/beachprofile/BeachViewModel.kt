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
    val line: String?,
    val name: String,
    val transportMode: String?,
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
     * Update favorite list
     */
    fun checkAndUpdateFavorites(beach: Beach) {
        viewModelScope.launch {
            _beachRepository.updateFavorites(beach)
            _isFavorited.value = _beachRepository.checkFavorite(beach)
        }
    }

    /**
     * Check if beach is in favorite list
     */
    fun checkFavorite(beach: Beach) {
        _isFavorited.value = _beachRepository.checkFavorite(beach)
        Log.d("beachviewmodel, checkFavorite", "Favorittstatus changed: ${_isFavorited.value}")
    }

    init {
        loadBeachInfo()
        Log.d("ViewModelInit", "BeachViewModel using repository: $_beachRepository")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadBeachInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            val beachInfo: Beach? = _beachRepository.getBeach(beachName)
            val osloKommuneBeachInfo: Beach? = _osloKommuneRepository.getBeach(beachName)
            val lon = beachInfo?.pos?.lon
            val lat = beachInfo?.pos?.lat
            var busStations: Busstations?

            if ((lon == null) || (lat == null)) {
                //Fetch ID for all buss stations based on name
                busStations = _enTurRepositoryGeocoderRepository.fetchBusRouteName(beachName)
            } else {
                //Fetch ID for all buss stations based on location
                busStations = _enTurRepositoryGeocoderRepository.fetchBusRouteLoc(
                    lat.toDouble(), lon.toDouble()
                )
                if (busStations?.busstation?.isEmpty() == true) {
                    busStations = _enTurRepositoryGeocoderRepository.fetchBusRouteName(beachName)
                }
            }
            val uniqueBusRoutes = mutableSetOf<BusRoute>()
            busStations?.busstation?.forEach { station ->
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
            }
            _isLoading.value = false
        }
    }
}