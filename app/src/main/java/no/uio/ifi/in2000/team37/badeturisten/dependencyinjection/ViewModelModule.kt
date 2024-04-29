package no.uio.ifi.in2000.team37.badeturisten.data.dependencyinjection

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.uio.ifi.in2000.team37.badeturisten.domain.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.LocationRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.MetAlertsRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.ui.home.HomeViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.favourites.FavouritesViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.search.SearchViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile.BeachViewModel

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideHomeViewModel(
        @SuppressLint("StaticFieldLeak") context: Context,
        locationRepository: LocationRepository,
        locationForecastRepository: LocationForecastRepository,
        osloKommuneRepository: OsloKommuneRepository,
        beachRepository: BeachRepository,
        metAlertsRepository: MetAlertsRepository,
    ): HomeViewModel {
        return HomeViewModel(
            context,
            locationRepository,
            locationForecastRepository,
            osloKommuneRepository,
            beachRepository,
            metAlertsRepository
        )
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
}