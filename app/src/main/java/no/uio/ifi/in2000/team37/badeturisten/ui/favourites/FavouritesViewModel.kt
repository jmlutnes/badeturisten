package no.uio.ifi.in2000.team37.badeturisten.ui.favourites

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import javax.inject.Inject

data class FavouritesUIState(
    val favourites: List<Beach> = listOf()
)

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class FavouritesViewModel @Inject constructor(
    private val _beachesRepository: BeachRepository
): ViewModel() {
    val favouritesState: StateFlow<FavouritesUIState> = _beachesRepository.getFavouriteObservations()
        .map { FavouritesUIState(favourites = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FavouritesUIState()
        )

    init {
        viewModelScope.launch {
            _beachesRepository.updateFavourites(null)
            //val osloKommuneBeachInfo: List<Beach> = _osloKommuneRepository.getFavourites()
        }
    }
}