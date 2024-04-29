package no.uio.ifi.in2000.team37.badeturisten.dependencyinjection

import android.os.Build
import androidx.annotation.RequiresApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.MetAlertsRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.ui.home.HomeViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.favourites.FavouritesViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.search.SearchViewModel

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideHomeViewModel(
        locationForecastRepository: LocationForecastRepository,
        osloKommuneRepository: OsloKommuneRepository,
        beachRepository: BeachRepository,
        metAlertsRepository: MetAlertsRepository,
    ): HomeViewModel {
        return HomeViewModel(locationForecastRepository, osloKommuneRepository, beachRepository, metAlertsRepository)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideFavouritesViewModel(
        beachRepository: BeachRepository
    ): FavouritesViewModel {
        return FavouritesViewModel(beachRepository)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideSearchViewModel(
        osloKommuneRepository: OsloKommuneRepository,
        beachRepository: BeachRepository
    ): SearchViewModel {
        return SearchViewModel(osloKommuneRepository, beachRepository)
    }

    /*@RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideBeachViewModel(
        savedStateHandle: SavedStateHandle,
        osloKommuneRepository: OsloKommuneRepository,
        beachesRepository: BeachRepository,
        enTurRepositoryGeocoderRepository: EnTurGeocoderRepository,
        enTurRepositoryJourneyPlanner: EnTurJourneyPlannerRepository
    ): BeachViewModel {
        return BeachViewModel(savedStateHandle, osloKommuneRepository, beachesRepository,
            enTurRepositoryGeocoderRepository, enTurRepositoryJourneyPlanner)
    }*/
}