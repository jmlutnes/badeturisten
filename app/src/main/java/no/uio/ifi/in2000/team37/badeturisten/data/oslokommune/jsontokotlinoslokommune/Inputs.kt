package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune

import com.google.gson.annotations.SerializedName
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.Item

data class Inputs(
    @SerializedName("items")
    val items: List<Item>
)