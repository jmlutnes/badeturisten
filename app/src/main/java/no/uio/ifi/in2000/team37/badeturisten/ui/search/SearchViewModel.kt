package no.uio.ifi.in2000.team37.badeturisten.ui.search

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.CombineBeachesUseCase
import no.uio.ifi.in2000.team37.badeturisten.domain.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BeachInfoForHomescreen
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.ui.home.BeachesUIState
import javax.inject.Inject

data class SokKommuneBeachList(
    val beachList: List<Beach> = listOf()
)

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class SearchViewModel @Inject constructor (
    private val _osloKommuneRepository: OsloKommuneRepository,
    private val _beachRepository: BeachRepository
): ViewModel() {
    var badevakt = mutableStateOf(false)
    var barnevennlig = mutableStateOf(false)
    var grill = mutableStateOf(false)
    var kiosk = mutableStateOf(false)
    var tilpasning = mutableStateOf(false)
    var toalett = mutableStateOf(false)
    var badebrygge = mutableStateOf(false)

    private val _beachDetails = MutableStateFlow<Map<String, BeachInfoForHomescreen?>>(emptyMap())
    val beachDetails: StateFlow<Map<String, BeachInfoForHomescreen?>> = _beachDetails.asStateFlow()

    private val _sokResultater = MutableStateFlow(SokKommuneBeachList())
    val sokResultater: StateFlow<SokKommuneBeachList> = _sokResultater.asStateFlow()

    var beachState: MutableStateFlow<BeachesUIState> = MutableStateFlow(BeachesUIState())

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val beachDetails = getBeachInfo()
                _beachDetails.value = beachDetails

            } catch (e: Exception) {
                Log.e("HomeViewModel", "Feil ved beachinfo: ${e.message}")
                _beachDetails.value = emptyMap()
            }
            loadBeachesByFilter(
                badevakt = false,
                barnevennlig = false,
                grill = false,
                kiosk = false,
                tilpasning = false,
                toalett = false,
                badebrygge = false
            )
            beachState.update {
                BeachesUIState(CombineBeachesUseCase(_beachRepository, _osloKommuneRepository)())
            }
        }
    }
    private suspend fun getBeachInfo(): Map<String, BeachInfoForHomescreen?> {
        return _osloKommuneRepository.findAllWebPages()
    }
    suspend fun loadBeachesByFilter(
        badevakt: Boolean,
        barnevennlig: Boolean,
        grill: Boolean,
        kiosk: Boolean,
        tilpasning: Boolean,
        toalett: Boolean,
        badebrygge: Boolean
    ) {
        _isLoading.value = true
        try{
        coroutineScope {
            val oppdaterteStrender = async {
                _osloKommuneRepository.makeBeachesFacilities(
                    badevakt,
                    barnevennlig,
                    grill,
                    kiosk,
                    tilpasning,
                    toalett,
                    badebrygge
                )
            }

            _sokResultater.value = SokKommuneBeachList(oppdaterteStrender.await())
        }
        } finally {
            _isLoading.value = false
        }
    }

    fun updateFilterState(filter: String, state: Boolean) {
        when (filter) {
            "Badevakt" -> badevakt.value = state
            "Barnevennlig" -> barnevennlig.value = state
            "Grill" -> grill.value = state
            "Kiosk" -> kiosk.value = state
            "Tilpasning" -> tilpasning.value = state
            "Toalett" -> toalett.value = state
            "Badebrygge" -> badebrygge.value = state
        }
    }
}