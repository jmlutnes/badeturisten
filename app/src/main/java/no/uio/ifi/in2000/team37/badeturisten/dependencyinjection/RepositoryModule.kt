package no.uio.ifi.in2000.team37.badeturisten.dependencyinjection

import LocationRepositoryImp
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.entur.EnTurDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.entur.EnTurRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.EnTurJourneyPlannerDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.EnTurJourneyPlannerRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneDatasource
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurGeocoderRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurJourneyPlannerRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.MetAlertsRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.OsloKommuneRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideBeachRepository(dataSource: WaterTemperatureDataSource): BeachRepository {
        return BeachRepositoryImp(dataSource)
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

    @Provides
    @Singleton
    fun provideEnTurRepository(dataSource: EnTurDataSource): EnTurRepository {
        return EnTurRepositoryImp(dataSource)
    }
}