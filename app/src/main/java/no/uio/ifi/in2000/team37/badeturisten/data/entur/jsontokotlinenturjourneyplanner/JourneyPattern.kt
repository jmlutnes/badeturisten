package no.uio.ifi.in2000.team37.badeturisten.data.entur.jsontokotlinenturjourneyplanner


import com.google.gson.annotations.SerializedName

data class JourneyPattern(
    @SerializedName("line")
    val line: Line
)