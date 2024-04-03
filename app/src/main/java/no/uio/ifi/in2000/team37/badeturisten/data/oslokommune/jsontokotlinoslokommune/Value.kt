package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune

import com.google.gson.annotations.SerializedName

data class Value(
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("street_id")
    val streetId: Int,
    @SerializedName("street_name")
    val streetName: String
)