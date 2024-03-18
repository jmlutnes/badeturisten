package no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.jsontokotlin


import com.google.gson.annotations.SerializedName

data class Details(
    @SerializedName("air_pressure_at_sea_level")
    val airPressureAtSeaLevel: Double,
    @SerializedName("air_temperature")
    val airTemperature: Double,
    @SerializedName("cloud_area_fraction")
    val cloudAreaFraction: Double,
    @SerializedName("relative_humidity")
    val relativeHumidity: Double,
    @SerializedName("wind_from_direction")
    val windFromDirection: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double
)