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

data class BeachUIState(val beach: Beach? = null, val badevannsinfo: BadevannsInfo?)
//data class UiKommune(val badevannsinfo: BadevannsInfo?)

//Viewmodel som henter strom fra kun en strand
class BeachViewModel(savedStateHandle : SavedStateHandle): ViewModel() {
    private val beachName : String = checkNotNull(savedStateHandle["beachName"])
    private val waterTempRepository : WaterTemperatureRepository = WaterTemperatureRepository(
        WaterTemperatureDataSource()
    )

    private val _beachUIState = MutableStateFlow(BeachUIState(null,
        BadevannsInfo("", "", "")
    ))
    val beachUIState: StateFlow<BeachUIState> = _beachUIState.asStateFlow()

    //--------------------OsloKommune----------------//
    private val osloKommuneRepository =
        OsloKommuneRepository(datasource = OsloKommuneDatasource())
    //private val _UiKommune = MutableStateFlow<UiKommune>(UiKommune(null))
    //var UiKommune: StateFlow<UiKommune> = _UiKommune.asStateFlow()
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
        //loadKommune()
    }

    private fun loadBeachInfo() {
        viewModelScope.launch (Dispatchers.IO) {
            val beachinfo = waterTempRepository.getBeach(beachName)
            val osloKommuneBeachInfo = osloKommuneRepository.getBeach(beachName)
            val lon = beachinfo?.pos?.lat?.toDouble()
            val lat = beachinfo?.pos?.lon?.toDouble()
            println("lon:$lon \nlat:$lat")

            //if (lat != null && lon != null) {
                val vannkvalitet: BadevannsInfo? = osloKommuneRepository.finnNettside(beachName)
                _beachUIState.update { currentUIState ->
                    if(beachinfo!=null){
                        currentUIState.copy(beach = beachinfo, badevannsinfo = vannkvalitet)
                    }
                    else{
                        currentUIState.copy(beach = osloKommuneBeachInfo, badevannsinfo = vannkvalitet)
                    }
            /*waterTempRepository.loadBeach(beachName)*/
                }

        }
    }
    /*
    private fun loadKommune() {
        //Placeholder lokasjoner

        viewModelScope.launch(Dispatchers.IO)  {
            val lat = beachUIState.value.beach?.pos?.lat?.toDouble()
            val lon = beachUIState.value.beach?.pos?.lon?.toDouble()

            //println("lon:$lon \nlat:$lat")

            val vannkvalitet: BadevannsInfo? =
                lat?.let { lon?.let { it1 -> osloKommuneRepository.getVannkvalitet(it, it1) } }

            _UiKommune.update {
                return@update UiKommune(vannkvalitet)
            }

        }
    }

     */
}
