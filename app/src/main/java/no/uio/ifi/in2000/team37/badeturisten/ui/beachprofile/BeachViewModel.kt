package no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.Bussstasjoner
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurJourneyPlannerRepository

import no.uio.ifi.in2000.team37.badeturisten.model.beach.BadevannsInfo
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import javax.inject.Inject

data class BeachUIState(val beach: Beach? = null, val badevannsinfo: BadevannsInfo?, val kollektivRute: MutableList<Bussrute> = mutableListOf())
data class Bussrute(val linje: String, val navn: String, val transportMode: String)

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class BeachViewModel @Inject constructor(
    savedStateHandle : SavedStateHandle,
    private val _osloKommuneRepository: OsloKommuneRepositoryImp,
    private val _beachesRepository: BeachRepositoryImp,
    private val _enTurRepositoryGeocoderRepository: EnTurGeocoderRepositoryImp,
    private val _enTurRepositoryJourneyPlanner: EnTurJourneyPlannerRepository
): ViewModel() {
    private val beachName: String = checkNotNull(savedStateHandle["beachName"])
    private val _beachUIState = MutableStateFlow(BeachUIState(null, BadevannsInfo(
        null,
        null,
        null
    ),
        kollektivRute = mutableListOf<Bussrute>()
    ))
    val beachUIState: StateFlow<BeachUIState> = _beachUIState.asStateFlow()
    init { loadBeachInfo() }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadBeachInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val beachinfo = _beachesRepository.getBeach(beachName)
            val osloKommuneBeachInfo = _osloKommuneRepository.getBeach(beachName)
            val lon = beachinfo?.pos?.lon?.toDouble()
            val lat = beachinfo?.pos?.lat?.toDouble()
            println("lon:$lon \nlat:$lat")
            var bussstasjoner: Bussstasjoner? = null
            bussstasjoner = if((lon == null) || (lat == null)) {
                //Henter ID for alle bussstasjoner som finner basert paa navn
                _enTurRepositoryGeocoderRepository.hentBussruteName(beachName)
            } else{
                //Henter ID for alle busstasjoner som finner basert paa lokasjon
                _enTurRepositoryGeocoderRepository.hentBussruteLoc(lat, lon)
            }
            //}
            // Henter bussruter (linje og navn) basert paa id fra stasjoner
            // Set for ingen duplikater
            val unikeBussruter = mutableSetOf<Bussrute>()
            bussstasjoner?.bussstasjon?.forEach { stasjon ->
                stasjon.id?.let { id ->
                    /*_enTurRepositoryJourneyPlanner.hentBussruterMedId(id)?.let { bussruter ->
                        unikeBussruter.addAll(bussruter)
                    }*/
                }
            }
            val alleBussruter: MutableList<Bussrute> = unikeBussruter.toMutableList()
            val vannkvalitet: BadevannsInfo? = _osloKommuneRepository.finnNettside(beachName)
            _beachUIState.update { currentUIState ->
                if (beachinfo != null) {
                    currentUIState.copy(beach = beachinfo, badevannsinfo = vannkvalitet, alleBussruter)
                } else {
                    currentUIState.copy(beach = osloKommuneBeachInfo, badevannsinfo = vannkvalitet, alleBussruter)
                }
            }

        }
    }

     fun updateFavourites(beach: Beach?) {
         if (beach != null) {
             _beachesRepository.updateFavourites(beach)
         }
    }
}
