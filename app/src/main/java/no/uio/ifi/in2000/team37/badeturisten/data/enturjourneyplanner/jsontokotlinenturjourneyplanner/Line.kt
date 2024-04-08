package no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.jsontokotlinenturjourneyplanner


import com.google.gson.annotations.SerializedName

data class Line(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("publicCode")
    val publicCode: String,
    @SerializedName("transportMode")
    val transportMode: String
)