package no.uio.ifi.in2000.team37.badeturisten.dependencyinjection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.EnTurJourneyPlannerDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneDatasource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @EnTurHttpGeocoderHttpClient
    fun provideEnTurGeocoderHttpClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url("https://api.entur.io/geocoder/v1/")
                header("ET-Client-Name", "in2000study-application")
            }
            install(ContentNegotiation) { gson { } }
            install(HttpTimeout) {
                this.requestTimeoutMillis = 5000
                this.connectTimeoutMillis = 5000
                this.socketTimeoutMillis = 5000
            }
        }
    }

    @Provides
    @EnTurJourneyPlannerHttpClient
    fun provideEnTurJourneyPlannerHttpClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url("https://api.entur.io/journey-planner/v3/graphql")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("ET-Client-Name", "in2000study-application")
            }
            install(ContentNegotiation) { gson { } }
            install(HttpTimeout) {
                this.requestTimeoutMillis = 5000
                this.connectTimeoutMillis = 5000
                this.socketTimeoutMillis = 5000
            }
        }
    }

    // HttpClient for Water Temperature API
    @Provides
    @WaterTemperatureHttpClient
    fun provideWaterTemperatureHttpClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url("https://havvarsel-frost.met.no")
            }
            install(ContentNegotiation) { gson {} }
            install(HttpTimeout) {
                this.requestTimeoutMillis = 5000
                this.connectTimeoutMillis = 5000
                this.socketTimeoutMillis = 5000
            }
        }
    }

    // HttpClient for Location Forecast API
    @Provides
    @LocationForecastHttpClient
    fun provideLocationForecastHttpClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url("https://gw-uio.intark.uh-it.no/in2000/")
                header("X-Gravitee-API-Key", "91eb6bae-3896-4da4-8a6a-a3a5266bf179")
            }
            install(ContentNegotiation) { gson {} }
            install(HttpTimeout) {
                this.requestTimeoutMillis = 5000
                this.connectTimeoutMillis = 5000
                this.socketTimeoutMillis = 5000
            }
        }
    }

    // HttpClient for Met Alerts API
    @Provides
    @MetAlertsHttpClient
    fun provideMetAlertsHttpClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url("https://gw-uio.intark.uh-it.no/in2000/")
                header("X-Gravitee-API-Key", "91eb6bae-3896-4da4-8a6a-a3a5266bf179")
            }
            install(ContentNegotiation) { gson {} }
            install(HttpTimeout) {
                this.requestTimeoutMillis = 5000
                this.connectTimeoutMillis = 5000
                this.socketTimeoutMillis = 5000
            }
        }
    }

    // HttpClient for Oslo Kommune API
    @Provides
    @OsloKommuneHttpClient
    fun provideOsloKommuneHttpClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url("https://www.oslo.kommune.no")
            }
            install(ContentNegotiation) { gson {} }
            install(HttpTimeout) {
                this.requestTimeoutMillis = 5000
                this.connectTimeoutMillis = 5000
                this.socketTimeoutMillis = 5000
            }
        }
    }

    @Provides
    fun provideEnTurGeocoderDataSource(@EnTurHttpGeocoderHttpClient client: HttpClient): EnTurGeocoderDataSource {
        return EnTurGeocoderDataSource(client)
    }

    @Provides
    fun provideEnTurJourneyPlannerDataSource(@EnTurJourneyPlannerHttpClient client: HttpClient): EnTurJourneyPlannerDataSource {
        return EnTurJourneyPlannerDataSource(client)
    }

    @Provides
    fun provideWaterTemperatureDataSource(@WaterTemperatureHttpClient client: HttpClient): WaterTemperatureDataSource {
        return WaterTemperatureDataSource(client)
    }

    @Provides
    fun provideLocationForecastDataSource(@LocationForecastHttpClient client: HttpClient): LocationForecastDataSource {
        return LocationForecastDataSource(client)
    }

    @Provides
    fun provideMetAlertsDataSource(@MetAlertsHttpClient client: HttpClient): MetAlertsDataSource {
        return MetAlertsDataSource(client)
    }

    @Provides
    fun provideOsloKommuneDataSource(@OsloKommuneHttpClient client: HttpClient): OsloKommuneDatasource {
        return OsloKommuneDatasource(client)
    }
}