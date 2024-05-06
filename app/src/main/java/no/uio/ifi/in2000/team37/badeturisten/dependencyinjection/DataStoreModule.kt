package no.uio.ifi.in2000.team37.badeturisten.dependencyinjection

import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    /*@Singleton
    @Provides
    fun provideBeachDataStore(@ApplicationContext beach: Beach){ //: DataStore<Beach>
        *//*return BeachDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext,USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )*//*
    }*/
}