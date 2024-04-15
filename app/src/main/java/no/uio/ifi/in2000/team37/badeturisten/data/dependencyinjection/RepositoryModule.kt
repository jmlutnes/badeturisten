package no.uio.ifi.in2000.team37.badeturisten.data.dependencyinjection

import android.os.Build
import androidx.annotation.RequiresApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderRepository
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsRepository
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
//import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.EnTurJourneyPlannerRepository
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
    /*@Binds
    @Singleton
    abstract fun bindMyRepository(
        myRepositoryImpl: MyRepositoryImpl
    ): MyRepository*/

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideBeachRepository(waterTemperatureDataSource: WaterTemperatureDataSource): BeachRepository {
        return BeachRepository(waterTemperatureDataSource)
    }

    @Provides
    fun provideOsloKommuneRepository(osloKommuneDatasource: OsloKommuneDatasource): OsloKommuneRepository {
        return OsloKommuneRepository(osloKommuneDatasource)
    }

/*    @Provides
    fun provideWaterTemperatureDataSource(): WaterTemperatureDataSource {
        return WaterTemperatureDataSource()
    }*/

    @Provides
    fun provideLocationForecastRepository(locationForecastDataSource: LocationForecastDataSource): LocationForecastRepository {
        return LocationForecastRepository(locationForecastDataSource)
    }

    @Provides
    fun provideMetAlertsRepository( metAlertsDataSource: MetAlertsDataSource): MetAlertsRepository {
        return MetAlertsRepository(metAlertsDataSource)
    }
/*    @Provides
    fun provideEnTurJourneyPlannerRepository(): EnTurJourneyPlannerRepository {
        return EnTurJourneyPlannerRepository()
    }*/
    @Provides
    fun provideEnTurGeocoderRepository(enTurGeocoderDataSource: EnTurGeocoderDataSource): EnTurGeocoderRepository {
        return EnTurGeocoderRepository(enTurGeocoderDataSource)
    }

}