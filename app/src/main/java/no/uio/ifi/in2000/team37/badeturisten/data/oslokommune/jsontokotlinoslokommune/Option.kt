package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune

import com.google.gson.annotations.SerializedName

data class Option(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("count")
    val count: Int,
    @SerializedName("filterID")
    val filterID: String,
    @SerializedName("label")
    val label: String
)