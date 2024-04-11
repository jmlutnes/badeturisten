package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.jsontokotlinenturgeocoder


import com.google.gson.annotations.SerializedName

data class Lang(
    @SerializedName("defaulted")
    val defaulted: Boolean,
    @SerializedName("iso6391")
    val iso6391: String,
    @SerializedName("iso6393")
    val iso6393: String,
    @SerializedName("name")
    val name: String
)