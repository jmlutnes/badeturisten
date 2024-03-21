package no.uio.ifi.in2000.team37.badeturisten.data.OsloKommune


import com.google.gson.annotations.SerializedName
import no.uio.ifi.in2000.team37.badeturisten.data.OsloKommune.JsonToKotlinOsloKommune.Item

data class Inputs(
    @SerializedName("items")
    val items: List<Item>
)