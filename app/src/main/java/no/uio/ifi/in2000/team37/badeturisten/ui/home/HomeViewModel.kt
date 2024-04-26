package no.uio.ifi.in2000.team37.badeturisten.ui.home

import LocationRepository
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsRepository
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.WeatherWarning
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Pos
import no.uio.ifi.in2000.team37.badeturisten.domain.CombineBeachesUseCase
import no.uio.ifi.in2000.team37.badeturisten.domain.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.MetAlertsRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.WeatherWarning
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BeachInfoForHomescreen
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.ForecastNextHour

import javax.inject.Inject
import no.uio.ifi.in2000.team37.badeturisten.model.locationforecast.ForecastNextHour
import kotlin.math.*

data class MetAlertsUIState(
    val alerts: List<WeatherWarning> = listOf()
)

data class ForecastUIState(
    val forecastNextHour: ForecastNextHour? = null
)

data class BeachesUIState (
    val beaches: List<Beach> = listOf()
)


@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel @Inject constructor(
    private val _locationForecastRepository: LocationForecastRepository,
    private val _osloKommuneRepository: OsloKommuneRepository,
    private val _beachRepository: BeachRepository,
    private val _metAlertsRepository: MetAlertsRepository
): ViewModel() {
    //henter vaer melding
    val forecastState: StateFlow<ForecastUIState> = _locationForecastRepository.observeForecastNextHour()
        .map { ForecastUIState(forecastNextHour = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ForecastUIState()
        )

    //henter strender
    private val _beachDetails = MutableStateFlow<Map<String, BeachInfoForHomescreen?>>(emptyMap())
    val beachDetails: StateFlow<Map<String, BeachInfoForHomescreen?>> = _beachDetails.asStateFlow()

    var beachState: MutableStateFlow<BeachesUIState> = MutableStateFlow(BeachesUIState())

    //henter farevarsler
    val metAlertsState: StateFlow<MetAlertsUIState> = _metAlertsRepository.getMetAlertsObservations()
        .map { MetAlertsUIState(alerts = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MetAlertsUIState()
        )

    //Bruker lokasjon
    private val _beachLocation = MutableStateFlow<List<Pair<Beach, Int?>>>(emptyList())
    val beachLocation: StateFlow<List<Pair<Beach, Int?>>> = _beachLocation.asStateFlow()

    private val locationRepository = LocationRepository(context)
    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location.asStateFlow()

    init {
        viewModelScope.launch {
            _locationForecastRepository.loadForecastNextHour()
            _beachRepository.loadBeaches()
            _metAlertsRepository.getWeatherWarnings()
            loadBeachInfo()
            fetchLocationData()
            beachState.update {
                BeachesUIState(CombineBeachesUseCase(_beachRepository, _osloKommuneRepository)())
            }
            sortDistances()
        }
    }

    private fun fetchLocationData() {
        viewModelScope.launch {
            locationRepository.fetchLastLocation()
            var needCurrent: Boolean = false


            locationRepository.locationData.collect { newLocation ->
                if (newLocation?.latitude == null) {
                    locationRepository.fetchCurrentLocation()
                    needCurrent = true
                } else {
                    _location.value = newLocation
                }
            }
            if (needCurrent) {
                locationRepository.locationData.collect { newLocation ->
                    if (newLocation?.latitude != null) {
                        _location.value = newLocation
                    }
                }
            }
        }
    }

    private fun loadBeachInfo() {
        viewModelScope.launch {
            try {
                val beachDetails = getBeachInfo()
                _beachDetails.value = beachDetails
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Feil ved beachinfo: ${e.message}")
                _beachDetails.value = emptyMap()
            }
        }
    }

    private suspend fun getBeachInfo(): Map<String, BeachInfoForHomescreen?> {
        return _osloKommuneRepository.findAllWebPages()
    }

    private fun sortDistances() {
        val locationMap = emptyMap<Beach, Int>().toMutableMap()
        beachState.value.beaches.forEach { beach ->
            locationMap[beach] = locationDistance(beach.pos, _location.value)
        }
        val sortedBeachesByDistance = sortBeachesByValue(locationMap)
        viewModelScope.launch {
            try {
                _beachLocation.value = sortedBeachesByDistance
                println(beachLocation)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Feil ved beachinfo: ${e.message}")
                _beachLocation.value = emptyList()
            }
        }
        println(sortedBeachesByDistance)
    }


    fun locationDistance(loc1: Pos, loc2: Location?): Int {
        val earthRadius = 6371e3
        val lat1 = Math.toRadians(loc1.lat.toDouble())
        val lon1 = Math.toRadians(loc1.lon.toDouble())
        val lat2 = Math.toRadians(loc2?.latitude ?: 999.0)
        val lon2 = Math.toRadians(loc2?.longitude ?: 999.0)
        val dLat = lat2 - lat1
        val dLon = lon2 - lon1
        val a = sin(dLat / 2).pow(2) +
                cos(lat1) * cos(lat2) *
                sin(dLon / 2).pow(2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val avstand = earthRadius * c
        return avstand.roundToInt()
    }

    fun sortBeachesByValue(beaches: Map<Beach, Int?>): List<Pair<Beach, Int?>> {
        return beaches.toList().sortedBy { it.second }
    }
}

//Kan implementeres med dependency injection fremfor factory, saa kan endres ved implementasjon
class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

