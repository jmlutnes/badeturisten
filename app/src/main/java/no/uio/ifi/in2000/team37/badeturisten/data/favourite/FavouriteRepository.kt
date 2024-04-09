package no.uio.ifi.in2000.team37.badeturisten.data.favourite

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

class FavouriteRepository(private val favouriteDbHelper: FavouriteDbHelper) {
    //flow og metode for aa observere
    private val favouriteObservations: MutableStateFlow<List<Beach>> = MutableStateFlow<List<Beach>>(listOf())
    fun getObservations() = favouriteObservations.asStateFlow()

    //metoder: oppdatere flow
    fun add(beach: Beach) {
        val db = favouriteDbHelper.writableDatabase
        //legg til i db
        db.close()
    }

    fun delete(beach: Beach) {
        val db = favouriteDbHelper.writableDatabase
        //fjerne fra db
        db.close()
    }
}