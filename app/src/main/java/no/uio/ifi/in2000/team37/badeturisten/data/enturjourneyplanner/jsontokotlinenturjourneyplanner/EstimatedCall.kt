package no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.jsontokotlinenturjourneyplanner


import com.google.gson.annotations.SerializedName

data class EstimatedCall(
    @SerializedName("destinationDisplay")
    val destinationDisplay: DestinationDisplay,
    @SerializedName("expectedDepartureTime")
    val expectedDepartureTime: String,
    @SerializedName("serviceJourney")
    val serviceJourney: ServiceJourney
)