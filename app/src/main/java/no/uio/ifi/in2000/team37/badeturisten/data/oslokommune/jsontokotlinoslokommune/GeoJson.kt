package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune

import com.google.gson.annotations.SerializedName

data class GeoJson(
    @SerializedName("features")
    val features: List<Feature>,
    @SerializedName("type")
    val type: String
)