package no.uio.ifi.in2000.team37.badeturisten.dependencyinjection

import no.uio.ifi.in2000.team37.badeturisten.data.location.LocationRepositoryImp
import android.content.Context
import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.EnTurJourneyPlannerDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.EnTurJourneyPlannerRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneDatasource
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurGeocoderRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurJourneyPlannerRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.LocationRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.MetAlertsRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideBeachRepository(
        dataSource: WaterTemperatureDataSource,
        dataStore: DataStore<List<Beach>>,
        coroutineScope: CoroutineScope,
    ): BeachRepository {
        return BeachRepositoryImp(dataSource, dataStore, coroutineScope)
    }

    @Provides
    @Singleton
    fun provideOsloKommuneRepository(dataSource: OsloKommuneDatasource): OsloKommuneRepository {
        return OsloKommuneRepositoryImp(dataSource)
    }

    @Provides
    @Singleton
    fun provideLocationForecastRepository(dataSource: LocationForecastDataSource): LocationForecastRepository {
        return LocationForecastRepositoryImp(dataSource)
    }

    @Singleton
    @Provides
    fun provideLocationRepository(context: Context): LocationRepository {
        return LocationRepositoryImp(context)
    }

    @Provides
    @Singleton
    fun provideMetAlertsRepository(dataSource: MetAlertsDataSource): MetAlertsRepository {
        return MetAlertsRepositoryImp(dataSource)
    }

    @Provides
    @Singleton
    fun provideEnTurJourneyPlannerRepository(enTurJourneyPlannerDataSource: EnTurJourneyPlannerDataSource): EnTurJourneyPlannerRepository {
        return EnTurJourneyPlannerRepositoryImp(enTurJourneyPlannerDataSource)
    }

    @Provides
    @Singleton
    fun provideEnTurGeocoderRepository(dataSource: EnTurGeocoderDataSource): EnTurGeocoderRepository {
        return EnTurGeocoderRepositoryImp(dataSource)
    }
}