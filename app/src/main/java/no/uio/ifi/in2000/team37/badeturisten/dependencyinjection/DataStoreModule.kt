package no.uio.ifi.in2000.team37.badeturisten.dependencyinjection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BeachListSerializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private val Context.dataStore by dataStore(
        fileName = "favorites.json", serializer = BeachListSerializer
    )

    @Singleton
    @Provides
    fun provideBeachListDataStore(@ApplicationContext context: Context): DataStore<List<Beach>> {
        return context.dataStore
    }
}