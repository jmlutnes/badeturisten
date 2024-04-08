package no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.jsontokotlinenturjourneyplanner


import com.google.gson.annotations.SerializedName

data class JourneyPattern(
    @SerializedName("line")
    val line: Line
)