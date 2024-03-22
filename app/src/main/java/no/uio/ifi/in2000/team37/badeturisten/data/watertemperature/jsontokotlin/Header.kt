package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin



import com.google.gson.annotations.SerializedName

data class Header(
    @SerializedName("extra")
    val extra: Extra,
    @SerializedName("id")
    val id: Id
)