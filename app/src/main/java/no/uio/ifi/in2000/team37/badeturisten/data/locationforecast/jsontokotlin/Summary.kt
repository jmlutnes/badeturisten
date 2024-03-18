package no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.jsontokotlin


import com.google.gson.annotations.SerializedName

data class Summary(
    @SerializedName("symbol_code")
    val symbolCode: String
)