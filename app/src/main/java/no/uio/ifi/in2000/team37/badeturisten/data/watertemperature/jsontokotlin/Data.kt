package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.watertemperature

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("tseries")
    val tseries: List<Tsery>,
    @SerializedName("tstype")
    val tstype: String
)