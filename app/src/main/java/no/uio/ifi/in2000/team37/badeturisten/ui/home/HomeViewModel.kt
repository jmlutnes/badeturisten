package no.uio.ifi.in2000.team37.badeturisten.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.datastore.BeachDataStore
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.WeatherWarning
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Pos
import no.uio.ifi.in2000.team37.badeturisten.domain.CombineBeachesUseCase
import no.uio.ifi.in2000.team37.badeturisten.domain.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.MetAlertsRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BeachInfoForHomescreen
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.ForecastNextHour
import no.uio.ifi.in2000.team37.badeturisten.domain.LocationRepository
import java.io.IOException
import java.net.UnknownHostException

import javax.inject.Inject
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
@HiltViewModel
class HomeViewModel @Inject constructor(
    @SuppressLint("StaticFieldLeak") @ApplicationContext private val context: Context,
    private val _locationRepository: LocationRepository,
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

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _locationForecastRepository.loadForecastNextHour()
                _beachRepository.loadBeaches()
                _metAlertsRepository.getWeatherWarnings()
                loadBeachInfo()
                fetchLocationData()
                beachState.update {
                    BeachesUIState(CombineBeachesUseCase(_beachRepository, _osloKommuneRepository)())
                }
                sortDistances()
            } catch (e: UnknownHostException) {
                Log.e("HomeViewModel", "No internet connection available, unable to resolve host", e)
            } catch (e: IOException) {
                Log.e("HomeViewModel", "Network I/O error occurred", e)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "An unexpected error occurred", e)
            }
        }
    }

    private suspend fun fetchLocationData() {
        viewModelScope.launch {
            _locationRepository.fetchCurrentLocation()

            _locationRepository.locationData.collect { newLocation ->
                if (newLocation == null) {
                    _locationRepository.fetchLastLocation()
                } else {
                    _location.value = newLocation
                }
            }
        }
    }
    private fun _refreshBeachLocation(){
        viewModelScope.launch {
            try {
                Log.d("HomeViewModel", "Updating beach locations")
                _isLoading.value = true
                _beachLocation.value = emptyList()

                delay(100)
                fetchLocationData()
                delay(100)
                sortDistances()

            } catch (e: UnknownHostException) {
                Log.e("HomeViewModel", "Unable to resolve host: Internet connection might be down", e)
            } catch (e: IOException) {
                Log.e("HomeViewModel", "Network I/O error occurred during beach location refresh", e)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "An unexpected error occurred during beach location refresh", e)
            } finally {
                _isLoading.value = false
                Log.d("HomeViewModel", "Beach locations updated")
            }
        }
    }

    fun refreshBeachLocations(){
        _refreshBeachLocation()
    }

    private fun loadBeachInfo() {
        viewModelScope.launch {
            try {
                val beachDetails = getBeachInfo()
                _beachDetails.value = beachDetails
            } catch (e: UnknownHostException) {
                Log.e("HomeViewModel", "Unable to resolve hostname: ${e.message}")
            } catch (e: IOException) {
                Log.e("HomeViewModel", "Network I/O error: ${e.message}")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "General error occurred: ${e.message}")
                _beachDetails.value = emptyMap()
            }
        }
    }

    private suspend fun getBeachInfo(): Map<String, BeachInfoForHomescreen?> {
        return try {
            _osloKommuneRepository.findAllWebPages()
        } catch (e: UnknownHostException) {
            Log.e("HomeViewModel", "Network issue while fetching beach info: Unable to resolve hostname")
            emptyMap()
        } catch (e: IOException) {
            Log.e("HomeViewModel", "Network I/O issue while fetching beach info")
            emptyMap()
        }
    }

    private fun sortDistances() {
        val locationMap = emptyMap<Beach, Int>().toMutableMap()
        var teller = -1
        beachState.value.beaches.forEach { beach ->
            if(_location.value?.latitude != null) {
                locationMap[beach] = locationDistance(beach.pos, _location.value)
            }
            else{
                locationMap[beach] = teller
                teller--
            }
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

    private fun locationDistance(loc1: Pos, loc2: Location?): Int {
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

    private fun sortBeachesByValue(beaches: Map<Beach, Int?>): List<Pair<Beach, Int?>> {
        return beaches.toList().sortedBy { it.second }
    }


    val beach = BeachDataStore.context.beachFlow.asLiveData()

    // To save a beach
    fun saveBeach(beach: Beach) {
        viewModelScope.launch {
            _beachRepository.saveBeach(beach)
        }
    }
    /*
    init {
        viewModelScope.launch {
            DataStoreManager.exampleSettingFlow.collect {
                _exampleSetting.value = it
            }
        }
    }

    fun setExampleSetting(newValue: Boolean) {
        viewModelScope.launch {
            DataStoreManager.saveExampleSetting(newValue)
        }
    }

    fun saveBeach(beach: BeachProto.Beach) {
        _beachRepository.saveBeach(beach)
    }
    */
}

