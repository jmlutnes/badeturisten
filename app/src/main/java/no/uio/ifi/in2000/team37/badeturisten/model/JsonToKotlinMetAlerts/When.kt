package no.uio.ifi.in2000.team37.badeturisten.model.JsonToKotlinMetAlerts


import com.google.gson.annotations.SerializedName

data class When(
    @SerializedName("interval")
    val interval: List<String>
)