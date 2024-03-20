package no.uio.ifi.in2000.team37.badeturisten.data.OsloKommune


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("geoJson")
    val geoJson: GeoJson,
    @SerializedName("items")
    val items: List<Any>
)