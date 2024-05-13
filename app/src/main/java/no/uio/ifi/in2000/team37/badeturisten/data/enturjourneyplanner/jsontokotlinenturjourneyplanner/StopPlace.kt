package no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.jsontokotlinenturjourneyplanner


import com.google.gson.annotations.SerializedName

data class StopPlace(
    @SerializedName("estimatedCalls")
    val estimatedCalls: List<EstimatedCall>,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("transportMode")
    val transportMode: List<String>
)