package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune

import com.google.gson.annotations.SerializedName

data class jsontokotlin_kommune(
    @SerializedName("data")
    val data: Data,
    @SerializedName("meta")
    val meta: Meta
)