package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.jsontokotlinenturgeocoder


import com.google.gson.annotations.SerializedName

data class Geocoding(
    @SerializedName("attribution")
    val attribution: String,
    @SerializedName("engine")
    val engine: Engine,
    @SerializedName("query")
    val query: Query,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("version")
    val version: String
)