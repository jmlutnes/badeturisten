package no.uio.ifi.in2000.team37.badeturisten.data.beach

import android.content.Context
import androidx.datastore.core.DataStore
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BeachListSerializer

class BeachDataStoreManager(private val context: Context) {

    /*val dataStore: DataStore<List<Beach>> = context.createDataStore(
        fileName = "beach_prefs.pb",
        serializer = BeachListSerializer
    )*/

    /*suspend fun saveBeach(beach: Beach) {
        dataStore.updateData { beach }
    }*/

    //val beachFlow: Flow<Beach> = dataStore.data
}