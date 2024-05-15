package no.uio.ifi.in2000.team37.badeturisten.dependencyinjection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.LocationRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.MetAlertsRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.ui.favorites.FavoritesViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.home.HomeViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.search.SearchViewModel

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Provides
    fun provideHomeViewModel(
        locationRepository: LocationRepository,
        locationForecastRepository: LocationForecastRepository,
        osloKommuneRepository: OsloKommuneRepository,
        beachRepository: BeachRepository,
        metAlertsRepository: MetAlertsRepository,
    ): HomeViewModel {
        return HomeViewModel(
            locationRepository,
            locationForecastRepository,
            osloKommuneRepository,
            beachRepository,
            metAlertsRepository
        )
    }

    @Provides
    fun provideFavoritesViewModel(
        osloKommuneRepository: OsloKommuneRepository,
        beachRepository: BeachRepository,
    ): FavoritesViewModel {
        return FavoritesViewModel(osloKommuneRepository, beachRepository)
    }

    @Provides
    fun provideSearchViewModel(
        osloKommuneRepository: OsloKommuneRepository,
        beachRepository: BeachRepository,
    ): SearchViewModel {
        return SearchViewModel(osloKommuneRepository, beachRepository)
    }
}