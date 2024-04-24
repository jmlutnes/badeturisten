package no.uio.ifi.in2000.team37.badeturisten.ui.search

import android.util.Log
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BadeinfoForHomescreen
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

data class SokKommuneBeachList(
    val beachList: List<Beach> = listOf()
)
class SokViewModel: ViewModel() {
    var badevakt = mutableStateOf(false)
    var barnevennlig = mutableStateOf(false)
    var grill = mutableStateOf(false)
    var kiosk = mutableStateOf(false)
    var tilpasning = mutableStateOf(false)
    var toalett = mutableStateOf(false)
    var badebrygge = mutableStateOf(false)

    private val osloKommuneRepository = OsloKommuneRepository()
    private val _beachDetails = MutableStateFlow<Map<String, BadeinfoForHomescreen?>>(emptyMap())
    val beachDetails: StateFlow<Map<String, BadeinfoForHomescreen?>> = _beachDetails.asStateFlow()
    private val _sokResultater = MutableStateFlow(SokKommuneBeachList())
    val sokResultater: StateFlow<SokKommuneBeachList> = _sokResultater.asStateFlow()


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
        }
    }
    private suspend fun getBeachInfo(): Map<String, BadeinfoForHomescreen?> {
        return osloKommuneRepository.findAllWebPages()
    }
    fun loadBeachesByFilter(
        badevakt: Boolean,
        barnevennlig: Boolean,
        grill: Boolean,
        kiosk: Boolean,
        tilpasning: Boolean,
        toalett: Boolean,
        badebrygge: Boolean
    ) {
        viewModelScope.launch {
            val oppdaterteStrender = osloKommuneRepository.makeBeachesFacilities(
                badevakt,
                barnevennlig,
                grill,
                kiosk,
                tilpasning,
                toalett,
                badebrygge
            )
            _sokResultater.value = SokKommuneBeachList(oppdaterteStrender)
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