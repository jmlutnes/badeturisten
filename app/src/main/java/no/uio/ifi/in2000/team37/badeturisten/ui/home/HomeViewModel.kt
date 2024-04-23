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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsRepository
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.WeatherWarning
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Pos
import no.uio.ifi.in2000.team37.badeturisten.domain.CombineBeachesUseCase
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BadeinfoForHomescreen
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
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



@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(@SuppressLint("StaticFieldLeak") context: Context): ViewModel() {

    //henter vaer melding
    private val _locationForecastRepository : LocationForecastRepository = LocationForecastRepository(dataSource = LocationForecastDataSource())

    val forecastState: StateFlow<ForecastUIState> = _locationForecastRepository.observeForecastNextHour()
        .map { ForecastUIState(forecastNextHour = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ForecastUIState()
        )

    //henter strender
    private val _osloKommuneRepository = OsloKommuneRepository()
    private val _beachesRepository = BeachRepository()
    private val _beachDetails = MutableStateFlow<Map<String, BadeinfoForHomescreen?>>(emptyMap())
    val beachDetails: StateFlow<Map<String, BadeinfoForHomescreen?>> = _beachDetails.asStateFlow()

    var beachState: MutableStateFlow<BeachesUIState> = MutableStateFlow(BeachesUIState())

    //henter farevarsler
    private val _metAlertsRepository = MetAlertsRepository(MetAlertsDataSource())
    val metAlertsState: StateFlow<MetAlertsUIState> = _metAlertsRepository.getMetAlertsObservations()
        .map { MetAlertsUIState(alerts = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MetAlertsUIState()
        )
    //Bruker lokasjon
    private val _beachLocation = MutableStateFlow<Map<Beach, Int?>>(emptyMap())
    val beachLocation: StateFlow<Map<Beach, Int?>> = _beachLocation.asStateFlow()
    private val locationRepository = LocationRepository(context)
    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location.asStateFlow()
    init {
        viewModelScope.launch {
            _locationForecastRepository.loadForecastNextHour()
            _beachesRepository.loadBeaches()
            _metAlertsRepository.getWeatherWarnings()
            loadBeachInfo()
            fetchLocationData()
            beachState.update {
                BeachesUIState(CombineBeachesUseCase(_beachesRepository, _osloKommuneRepository)())
            }
            sortDistances()
        }
    }
    private fun fetchLocationData() {
        viewModelScope.launch {
            locationRepository.fetchLastLocation()
            locationRepository.locationData.collect { newLocation ->
                _location.value = newLocation
            }
        }
    }
    fun loadBeachInfo() {
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

    private suspend fun getBeachInfo(): Map<String, BadeinfoForHomescreen?> {
        return _osloKommuneRepository.finnAlleNettside()
    }

    private fun sortDistances(){
        val locationMap = emptyMap<Beach, Int>().toMutableMap()
        beachState.value.beaches.forEach{ beach ->
                locationMap[beach] = location.value?.let { locationDistance(beach.pos, it) }!!

        }
        viewModelScope.launch {
            try {
                val beachLocation = locationMap
                _beachLocation.value = beachLocation
                println(beachLocation)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Feil ved beachinfo: ${e.message}")
                _beachLocation.value = emptyMap()
            }
        }

    }
}
fun locationDistance(loc1: Pos, loc2: Location): Int {
    val earthRadius = 6371e3
    val lat1 = Math.toRadians(loc1.lat.toDouble())
    val lon1 = Math.toRadians(loc1.lon.toDouble())
    val lat2 = Math.toRadians(loc2.latitude)
    val lon2 = Math.toRadians(loc2.longitude)
    val dLat = lat2 - lat1
    val dLon = lon2 - lon1
    val a = sin(dLat / 2).pow(2) +
            cos(lat1) * cos(lat2) *
            sin(dLon / 2).pow(2)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    val avstand = earthRadius * c
    val avrundet = avstand.roundToInt()//i meter
    return avrundet
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

