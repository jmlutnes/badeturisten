package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.jsontokotlinenturgeocoder


import com.google.gson.annotations.SerializedName

data class jsontokotlinenturgeocoder(
    @SerializedName("bbox")
    val bbox: List<Double>,
    @SerializedName("features")
    val features: List<Feature>,
    @SerializedName("geocoding")
    val geocoding: Geocoding,
    @SerializedName("type")
    val type: String
)