package no.uio.ifi.in2000.team37.badeturisten.data.dependencyinjection

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.EnTurJourneyPlannerRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.ui.home.HomeViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.favourites.FavouritesViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.search.SearchViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.BeachViewModel

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideHomeViewModel(
        locationForecastRepository: LocationForecastRepositoryImp,
        osloKommuneRepository: OsloKommuneRepositoryImp,
        beachRepository: BeachRepositoryImp,
        metAlertsRepository: MetAlertsRepositoryImp,
    ): HomeViewModel {
        return HomeViewModel(locationForecastRepository, osloKommuneRepository, beachRepository, metAlertsRepository)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideFavouritesViewModel(
        beachRepository: BeachRepositoryImp
    ): FavouritesViewModel {
        return FavouritesViewModel(beachRepository)
    }

    @Provides
    fun provideHomeViewModel(
        osloKommuneRepository: OsloKommuneRepositoryImp
    ): SearchViewModel {
        return SearchViewModel(osloKommuneRepository)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideBeachViewModel(
        savedStateHandle : SavedStateHandle,
        osloKommuneRepository: OsloKommuneRepositoryImp,
        beachesRepository: BeachRepositoryImp,
        enTurRepositoryGeocoderRepository: EnTurGeocoderRepositoryImp,
        enTurRepositoryJourneyPlanner: EnTurJourneyPlannerRepositoryImp
    ): BeachViewModel {
        return BeachViewModel(savedStateHandle, osloKommuneRepository, beachesRepository, enTurRepositoryGeocoderRepository, enTurRepositoryJourneyPlanner)
    }
}