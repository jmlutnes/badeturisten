package no.uio.ifi.in2000.team37.badeturisten.data.dependencyinjection

import android.os.Build
import androidx.annotation.RequiresApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepositoryImp
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

/*
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
*/
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    //men binds brukes paa abstrakte
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideBeachRepository(waterTemperatureDataSource: WaterTemperatureDataSource): BeachRepositoryImp {
        return BeachRepositoryImp(waterTemperatureDataSource)
    }

    @Provides
    fun provideOsloKommuneRepository(osloKommuneDatasource: OsloKommuneDatasource): OsloKommuneRepositoryImp {
        return OsloKommuneRepositoryImp(osloKommuneDatasource)
    }

    @Provides
    fun provideLocationForecastRepository(locationForecastDataSource: LocationForecastDataSource): LocationForecastRepositoryImp {
        return LocationForecastRepositoryImp(locationForecastDataSource)
    }

    @Provides
    fun provideMetAlertsRepository( metAlertsDataSource: MetAlertsDataSource): MetAlertsRepositoryImp {
        return MetAlertsRepositoryImp(metAlertsDataSource)
    }
    @Provides
    fun provideEnTurJourneyPlannerRepository(enTurJourneyPlannerDataSource: EnTurJourneyPlannerDataSource): EnTurJourneyPlannerRepositoryImp {
        return EnTurJourneyPlannerRepositoryImp(enTurJourneyPlannerDataSource)
    }

    @Provides
    fun provideEnTurGeocoderRepository(enTurGeocoderDataSource: EnTurGeocoderDataSource): EnTurGeocoderRepositoryImp {
        return EnTurGeocoderRepositoryImp(enTurGeocoderDataSource)
    }

}