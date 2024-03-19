package no.uio.ifi.in2000.team37.badeturisten.data.OsloKommune


import com.google.gson.annotations.SerializedName

data class Inputs(
    @SerializedName("items")
    val items: List<Item>
)