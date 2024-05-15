package no.uio.ifi.in2000.team37.badeturisten.ui.search

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.CombineBeachesUseCase
import no.uio.ifi.in2000.team37.badeturisten.domain.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BeachAndBeachInfo
import no.uio.ifi.in2000.team37.badeturisten.ui.home.BeachesUIState
import javax.inject.Inject

data class SokKommuneBeachList(
    val beachList: List<Beach> = listOf(),
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val _osloKommuneRepository: OsloKommuneRepository,
    private val _beachRepository: BeachRepository,
) : ViewModel() {
    var lifeGuard = mutableStateOf(false)
    var childFriendly = mutableStateOf(false)
    var grill = mutableStateOf(false)
    var kiosk = mutableStateOf(false)
    var accessible = mutableStateOf(false)
    var toilets = mutableStateOf(false)
    var divingTower = mutableStateOf(false)

    private val _beachDetails = MutableStateFlow<Map<String, BeachAndBeachInfo?>>(emptyMap())
    val beachDetails: StateFlow<Map<String, BeachAndBeachInfo?>> = _beachDetails.asStateFlow()

    private val _searchResults = MutableStateFlow(SokKommuneBeachList())
    val searchResults: StateFlow<SokKommuneBeachList> = _searchResults.asStateFlow()

    var beachState: MutableStateFlow<BeachesUIState> = MutableStateFlow(BeachesUIState())

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isConnectivityIssue = MutableStateFlow(false)
    val isConnectivityIssue = _isConnectivityIssue.asStateFlow()


    init {
        viewModelScope.launch {
            try {
                val beachDetails = getBeachInfo()
                _beachDetails.value = beachDetails

            } catch (e: Exception) {
                Log.e("HomeViewModel", "Feil ved henting av beachDetails: ${e.message}")
                _beachDetails.value = emptyMap()
                _isConnectivityIssue.update { true }
            }
            loadIntersectedBeaches(
                lifeguard = false,
                childFriendly = false,
                grill = false,
                kiosk = false,
                accessible = false,
                toilets = false,
                divingTower = false
            )
            beachState.update {
                BeachesUIState(CombineBeachesUseCase(_beachRepository, _osloKommuneRepository)())
            }

            if (beachState.value.beaches.isEmpty()) {
                _isConnectivityIssue.update { true }
            } else _isConnectivityIssue.update { false }
        }
    }

    /**
     * Fetches all the bathing sites from Oslo Commune website by calling findAllWebPages().
     * Returns a map with the name of the beach as key, and the bathing site information as value.
     */
    private suspend fun getBeachInfo(): Map<String, BeachAndBeachInfo?> {
        return _osloKommuneRepository.findAllWebPages()
    }

    /**
     * Fetches all the selected facilities and iterates asynchronously through each facility.
     * Then the resulting lists will call method to find the intersecting bathing sites.
     * Lastly a list with only intersecting bathing sites are returned.
     */
    suspend fun loadIntersectedBeaches(
        lifeguard: Boolean,
        childFriendly: Boolean,
        grill: Boolean,
        kiosk: Boolean,
        accessible: Boolean,
        toilets: Boolean,
        divingTower: Boolean,
    ) {
        _isLoading.value = true
        try {
            coroutineScope {
                val tasks = mutableListOf<Deferred<List<Beach>>>()
                if (lifeguard) tasks.add(async {
                    _osloKommuneRepository.makeBeachesFacilities(
                        lifeguard = true,
                        childFriendly = false,
                        grill = false,
                        kiosk = false,
                        accessible = false,
                        toilets = false,
                        divingTower = false
                    )
                })
                if (childFriendly) tasks.add(async {
                    _osloKommuneRepository.makeBeachesFacilities(
                        lifeguard = false,
                        childFriendly = true,
                        grill = false,
                        kiosk = false,
                        accessible = false,
                        toilets = false,
                        divingTower = false
                    )
                })
                if (grill) tasks.add(async {
                    _osloKommuneRepository.makeBeachesFacilities(
                        lifeguard = false,
                        childFriendly = false,
                        grill = true,
                        kiosk = false,
                        accessible = false,
                        toilets = false,
                        divingTower = false
                    )
                })
                if (kiosk) tasks.add(async {
                    _osloKommuneRepository.makeBeachesFacilities(
                        lifeguard = false,
                        childFriendly = false,
                        grill = false,
                        kiosk = true,
                        accessible = false,
                        toilets = false,
                        divingTower = false
                    )
                })
                if (accessible) tasks.add(async {
                    _osloKommuneRepository.makeBeachesFacilities(
                        lifeguard = false,
                        childFriendly = false,
                        grill = false,
                        kiosk = false,
                        accessible = true,
                        toilets = false,
                        divingTower = false
                    )
                })
                if (toilets) tasks.add(async {
                    _osloKommuneRepository.makeBeachesFacilities(
                        lifeguard = false,
                        childFriendly = false,
                        grill = false,
                        kiosk = false,
                        accessible = false,
                        toilets = true,
                        divingTower = false
                    )
                })
                if (divingTower) tasks.add(async {
                    _osloKommuneRepository.makeBeachesFacilities(
                        lifeguard = false,
                        childFriendly = false,
                        grill = false,
                        kiosk = false,
                        accessible = false,
                        toilets = false,
                        divingTower = true
                    )
                })
                val results = tasks.awaitAll()
                val intersectedResults = findIntersection(results)
                _isConnectivityIssue.update { false }
                _searchResults.value = SokKommuneBeachList(intersectedResults)
            }
        } catch (e: Exception) {
            Log.e("SearchViewModel", "Error loading intersected beaches: ${e.message}")
            _searchResults.value = SokKommuneBeachList(emptyList())
            _isConnectivityIssue.update { true }
        } finally {
            _isLoading.value = false
        }
    }

    /**
     * Goes through a list with a list of beaches and returns the intersection of the beaches in the lists
     */
    private fun findIntersection(lists: List<List<Beach>>): List<Beach> {
        if (lists.isEmpty()) return emptyList()
        return lists.reduce { acc, list ->
            acc.intersect(list.toSet()).toList()
        }
    }

    /**
     * Updates the boolean values in the view model for each facility that has been selected
     */
    fun updateFilterState(filter: String, state: Boolean) {
        when (filter) {
            "Badevakt" -> lifeGuard.value = state
            "Barnevennlig" -> childFriendly.value = state
            "Grill" -> grill.value = state
            "Kiosk" -> kiosk.value = state
            "Tilpasning" -> accessible.value = state
            "Toalett" -> toilets.value = state
            "Badebrygge" -> divingTower.value = state
        }
    }
}