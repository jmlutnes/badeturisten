package no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.OsloKommune.BadevannsInfo
import no.uio.ifi.in2000.team37.badeturisten.data.OsloKommune.OsloKommuneDatasource
import no.uio.ifi.in2000.team37.badeturisten.data.OsloKommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

data class BeachUIState(val beach: Beach? = null)
data class UiKommune(val badevannsinfo: BadevannsInfo?)

//Viewmodel som henter strom fra kun en strand
class BeachViewModel(savedStateHandle : SavedStateHandle): ViewModel() {
    private val beachName : String = checkNotNull(savedStateHandle["beachName"])
    private val waterTempRepository : WaterTemperatureRepository = WaterTemperatureRepository(
        WaterTemperatureDataSource()
    )

    private val _beachUIState = MutableStateFlow(BeachUIState())
    val beachUIState: StateFlow<BeachUIState> = _beachUIState.asStateFlow()

    //--------------------OsloKommune----------------//
    private val osloKommuneRepository =
        OsloKommuneRepository(datasource = OsloKommuneDatasource())
    private val _UiKommune = MutableStateFlow<UiKommune>(UiKommune(null))
    var UiKommune: StateFlow<UiKommune> = _UiKommune.asStateFlow()
    //-----------------------------------------------//

    /*val waterTemperatureState: StateFlow<WaterTemperatureUIState> = waterTempRepository.getObservations()
        .map { WaterTemperatureUIState(beaches = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = WaterTemperatureUIState()
        )*/



    init {
        loadBeachInfo()
        loadKommune()
    }

    private fun loadBeachInfo() {
        viewModelScope.launch (Dispatchers.IO) {
            val beachinfo = waterTempRepository.getBeach(beachName)
            _beachUIState.update {beachUIState ->
                beachUIState.copy(beach = beachinfo)
            }
            /*waterTempRepository.loadBeach(beachName)*/
        }
    }
    private fun loadKommune() {
        //Placeholder lokasjoner
        val lon = 59.97023
        val lat = 10.61805

        viewModelScope.launch {
            //Returnerer en string som inneholder URL til badested
            val vannkvalitet: BadevannsInfo? =  osloKommuneRepository.getVannkvalitet(lat, lon)

            _UiKommune.update {
                UiKommune(vannkvalitet)
            }
        }
    }
}