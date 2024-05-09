package no.uio.ifi.in2000.team37.badeturisten.ui.favourites

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BeachAndBeachInfo
import javax.inject.Inject

data class FavouritesUIState(
    val favourites: List<Beach> = listOf()
)

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class FavouritesViewModel @Inject constructor(
    private val _osloKommuneRepository: OsloKommuneRepository,
    private val _beachRepository: BeachRepository
): ViewModel() {
    private val _beachDetails = MutableStateFlow<Map<String, BeachAndBeachInfo?>>(emptyMap())
    val beachDetails: StateFlow<Map<String, BeachAndBeachInfo?>> = _beachDetails.asStateFlow()

    val favouritesState: StateFlow<FavouritesUIState> = _beachRepository.getFavouriteObservations()
        .map { FavouritesUIState(favourites = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FavouritesUIState()
        )

    private suspend fun getBeachInfo(): Map<String, BeachAndBeachInfo?> {
        return _osloKommuneRepository.findAllWebPages()
    }

    init {
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

}