package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.jsontokotlinenturgeocoder


import com.google.gson.annotations.SerializedName

data class Engine(
    @SerializedName("author")
    val author: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("version")
    val version: String
)