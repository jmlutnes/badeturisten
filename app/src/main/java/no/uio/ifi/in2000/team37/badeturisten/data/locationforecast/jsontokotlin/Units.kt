package no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.jsontokotlin


import com.google.gson.annotations.SerializedName

data class Units(
    @SerializedName("air_pressure_at_sea_level")
    val airPressureAtSeaLevel: String,
    @SerializedName("air_temperature")
    val airTemperature: String,
    @SerializedName("cloud_area_fraction")
    val cloudAreaFraction: String,
    @SerializedName("precipitation_amount")
    val precipitationAmount: String,
    @SerializedName("relative_humidity")
    val relativeHumidity: String,
    @SerializedName("wind_from_direction")
    val windFromDirection: String,
    @SerializedName("wind_speed")
    val windSpeed: String
)