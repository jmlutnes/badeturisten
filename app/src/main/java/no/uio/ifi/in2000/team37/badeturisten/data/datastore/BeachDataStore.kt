package no.uio.ifi.in2000.team37.badeturisten.data.datastore

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

object BeachDataStore {

    private val BEACHES_KEY = stringPreferencesKey("beaches")

    suspend fun saveBeaches(beaches: List<Beach>) {

       /* beaches.dataStore.edit { preferences ->
            preferences[BEACHES_KEY] = beachesString
        }*/
    }

    /*fun loadBeaches(beach: Beach): Flow<List<Beach>> = context.dataStore.data
        .map { preferences ->
            preferences[BEACHES_KEY]?.let {
                json.decodeFromString<List<Beach>>(it)
            } ?: emptyList()
        }*/
}