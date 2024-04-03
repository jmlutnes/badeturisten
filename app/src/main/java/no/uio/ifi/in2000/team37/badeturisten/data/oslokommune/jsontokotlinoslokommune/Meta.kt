package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune

import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("center")
    val center: Center,
    @SerializedName("currentOffset")
    val currentOffset: Int,
    @SerializedName("displayType")
    val displayType: String,
    @SerializedName("filteredCount")
    val filteredCount: Int,
    @SerializedName("filters")
    val filters: List<Filter>,
    @SerializedName("inputs")
    val inputs: Inputs,
    @SerializedName("unfilteredCount")
    val unfilteredCount: Int
)