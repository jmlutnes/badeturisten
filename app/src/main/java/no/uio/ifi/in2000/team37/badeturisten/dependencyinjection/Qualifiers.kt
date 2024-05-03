package no.uio.ifi.in2000.team37.badeturisten.dependencyinjection

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EnTurHttpGeocoderHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EnTurJourneyPlannerHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocationForecastHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MetAlertsHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OsloKommuneHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WaterTemperatureHttpClient