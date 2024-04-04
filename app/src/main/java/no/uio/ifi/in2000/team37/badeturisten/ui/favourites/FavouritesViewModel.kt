package no.uio.ifi.in2000.team37.badeturisten.ui.favourites

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

data class FavouritesUIState(
    val favourites: List<Beach> = listOf()
)
@RequiresApi(Build.VERSION_CODES.O)
class FavouritesViewModel: ViewModel() {
    private val _beachRepository: BeachRepository = BeachRepository()
    private val _osloKommuneRepository: OsloKommuneRepository = OsloKommuneRepository()

    //har laget egen mutablestateflow og metode for aa observere i beachrepo
    val favouritesState: StateFlow<FavouritesUIState> = _beachRepository.getFavouriteObservations()
        .map { FavouritesUIState(favourites = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FavouritesUIState()
        )



    init {
        viewModelScope.launch {
            Log.d("FavViMo, ", "viMoSco.lau")
            _beachRepository.getFavourites()
            //val osloKommuneBeachInfo: List<Beach> = _osloKommuneRepository.getFavourites()
        }
    }
}