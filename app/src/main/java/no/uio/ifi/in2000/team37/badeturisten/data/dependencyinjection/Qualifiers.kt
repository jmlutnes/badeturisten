package no.uio.ifi.in2000.team37.badeturisten.data.dependencyinjection

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EnTurHttpClient

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