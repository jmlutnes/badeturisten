package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.jsontokotlinenturgeocoder


import com.google.gson.annotations.SerializedName

data class Query(
    @SerializedName("lang")
    val lang: Lang,
    @SerializedName("layers")
    val layers: List<String>,
    @SerializedName("parser")
    val parser: String,
    @SerializedName("private")
    val `private`: Boolean,
    @SerializedName("querySize")
    val querySize: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("sources")
    val sources: List<String>,
    @SerializedName("text")
    val text: String,
    @SerializedName("tokens")
    val tokens: List<String>
)