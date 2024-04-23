package no.uio.ifi.in2000.team37.badeturisten.data.dependencyinjection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.team37.badeturisten.data.entur.EnTurDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.EnTurJourneyPlannerDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneDatasource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import javax.inject.Singleton

/*@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient()
    }

    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(customConfig())
    }
}*/

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /*
        // General HttpClient configuration
        @Provides
        @Singleton
        fun provideGeneralHttpClient(): HttpClient {
            return HttpClient {
                install(JsonFeature) {
                    serializer = GsonSerializer()
                }
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.INFO
                }
                defaultRequest {
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                }
            }
        }
     */

    /*
        //EnTurGeocoderDataSource
        defaultRequest {
        url("https://api.entur.io/geocoder/v1/")
        header("ET-Client-Name", "in2000study-application")
        }
        //EnTurJourneyPlannerDataSource
        defaultRequest {
        url("https://api.entur.io/journey-planner/v3/graphql")
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        header("ET-Client-Name", "in2000study-application")
        }
    */
    // HttpClient for En Tur API
    @Provides
    fun provideEnTurHttpClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url("https://api.entur.io/")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(ContentNegotiation) { gson { } }
        }
    }
    // HttpClient for Water Temperature API
    @Provides
    @Singleton
    fun provideWaterTemperatureHttpClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url("https://havvarsel-frost.met.no")
            }
            install(ContentNegotiation) { gson {} }
        }
    }
    // HttpClient for Location Forecast API
    @Provides
    @Singleton
    fun provideLocationForecastHttpClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url("https://gw-uio.intark.uh-it.no/in2000/")
                header("X-Gravitee-API-Key", "91eb6bae-3896-4da4-8a6a-a3a5266bf179")
            }
            install(ContentNegotiation) { gson {} }
        }
    }
    // HttpClient for Met Alerts API
    @Provides
    @Singleton
    fun provideMetAlertsHttpClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url("https://gw-uio.intark.uh-it.no/in2000/")
                header("X-Gravitee-API-Key", "91eb6bae-3896-4da4-8a6a-a3a5266bf179")
            }
            install(ContentNegotiation) { gson {} }
        }
    }
    // HttpClient for Oslo Kommune API
    @Provides
    @Singleton
    fun provideOsloKommuneHttpClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url("https://www.oslo.kommune.no")
                header("X-Gravitee-API-Key", "your-api-key-here")
            }
            install(ContentNegotiation) { gson {} }
        }
    }

    // Each data source provider method
    @Provides
    fun provideEnTurDataSource(client: HttpClient): EnTurDataSource {
        return EnTurDataSource(client)
    }

    @Provides
    fun provideEnTurGeocoderDataSource(client: HttpClient): EnTurGeocoderDataSource {
        return EnTurGeocoderDataSource(client)
    }

    @Provides
    fun provideEnTurJourneyPlannerDataSource(client: HttpClient): EnTurJourneyPlannerDataSource {
        return EnTurJourneyPlannerDataSource(client)
    }

    @Provides
    fun provideWaterTemperatureDataSource(client: HttpClient): WaterTemperatureDataSource {
        return WaterTemperatureDataSource(client)
    }

    @Provides
    fun provideLocationForecastDataSource(client: HttpClient): LocationForecastDataSource {
        return LocationForecastDataSource(client)
    }

    @Provides
    fun provideMetAlertsDataSource(client: HttpClient): MetAlertsDataSource {
        return MetAlertsDataSource(client)
    }
    @Provides
    fun provideOsloKommuneDataSource(client: HttpClient): OsloKommuneDatasource {
        return OsloKommuneDatasource(client)
    }

    /*
    RETROFIT
        @Provides
        @Singleton
        fun provideRetrofitForApiWaterTempterature(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api-one.example.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        @Provides
        fun provideApiServiceWaterTempterature(retrofit: Retrofit): ApiServiceWaterTempterature {
            return retrofit.newBuilder()
                .baseUrl("https://api-one.example.com/")
                .build()
                .create(ApiServiceWaterTempterature::class.java)
        }

        @Provides
        @Singleton
        fun provideRetrofitForApiOsloKommune(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api-two.example.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        @Provides
        fun provideApiServiceOsloKommune(retrofit: Retrofit): ApiServiceOsloKommune {
            return retrofit.newBuilder()
                .baseUrl("https://api-two.example.com/")
                .build()
                .create(ApiServiceOsloKommune::class.java)
        }

        @Provides
        @Singleton
        fun provideRetrofitForApiMetAlerts(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api-two.example.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        @Provides
        fun provideApiServiceMetAlerts(retrofit: Retrofit): ApiServiceMetAlerts {
            return retrofit.newBuilder()
                .baseUrl("https://api-two.example.com/")
                .build()
                .create(ApiServiceMetAlerts::class.java)
        }

        @Provides
        @Singleton
        fun provideRetrofitForApiLocationForecast(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api-two.example.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        @Provides
        fun provideApiServiceLocationForecast(retrofit: Retrofit): ApiServiceLocationForecast {
            return retrofit.newBuilder()
                .baseUrl("https://api-two.example.com/")
                .build()
                .create(ApiServiceLocationForecast::class.java)
        }

        @Provides
        @Singleton
        fun provideRetrofitForApiEnTurJourneyPlanner(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api-two.example.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        @Provides
        fun provideApiServiceEnTurJourneyPlanner(retrofit: Retrofit): ApiServiceEnTurJourneyPlanner {
            return retrofit.newBuilder()
                .baseUrl("https://api-two.example.com/")
                .build()
                .create(ApiServiceEnTurJourneyPlanner::class.java)
        }

        @Provides
        @Singleton
        fun provideRetrofitForApiEnTurGeocoder(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api-two.example.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        @Provides
        fun provideApiServiceEnTurGeocoder(retrofit: Retrofit): ApiServiceEnTurGeocoder {
            return retrofit.newBuilder()
                .baseUrl("https://api-two.example.com/")
                .build()
                .create(ApiServiceEnTurGeocoder::class.java)
        }

        @Provides
        @Singleton
        fun provideRetrofitForApiEnTur(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api-two.example.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        @Provides
        fun provideApiServiceEnTur(retrofit: Retrofit): ApiServiceEnTur {
            return retrofit.newBuilder()
                .baseUrl("https://api-two.example.com/")
                .build()
                .create(ApiServiceEnTur::class.java)
        }*/
}