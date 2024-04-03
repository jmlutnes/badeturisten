package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune

import com.google.gson.annotations.SerializedName

data class Filter(
    @SerializedName("filterType")
    val filterType: String,
    @SerializedName("heading")
    val heading: String,
    @SerializedName("options")
    val options: List<Option>
)