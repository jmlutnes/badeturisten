package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune


import com.google.gson.annotations.SerializedName

data class Algolia(
    @SerializedName("apiKey")
    val apiKey: String,
    @SerializedName("appId")
    val appId: String,
    @SerializedName("indexName")
    val indexName: String
)