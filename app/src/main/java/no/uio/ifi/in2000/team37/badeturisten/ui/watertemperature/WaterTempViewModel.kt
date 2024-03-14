package no.uio.ifi.in2000.team37.badeturisten.ui.watertemperature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureRepository
import no.uio.ifi.in2000.team37.badeturisten.model.Beach.Beach

data class WaterTemperatureUIState (
    val beaches: List<Beach> = listOf()
)
class WaterTempViewModel: ViewModel() {


    val waterTemperatureRepository = WaterTemperatureRepository(WaterTemperatureDataSource())

    val waterTemperatureState: StateFlow<WaterTemperatureUIState> = waterTemperatureRepository.getObservations()
        .map { WaterTemperatureUIState(beaches = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = WaterTemperatureUIState()
        )
    init {
        viewModelScope.launch {
            Log.d("water view model", "failed to loadstream")
            // oppdaterer stream for beaches
            waterTemperatureRepository.loadBeaches()
            Log.d("water view model", "failed to loadstream")
        }
    }


}