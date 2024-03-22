package no.uio.ifi.in2000.team37.badeturisten.data.metalerts.jsontokotlinmetalerts


import com.google.gson.annotations.SerializedName

data class Properties(
    @SerializedName("altitude_above_sea_level")
    val altitudeAboveSeaLevel: Int,
    @SerializedName("area")
    val area: String,
    @SerializedName("awareness_level")
    val awarenessLevel: String,
    @SerializedName("awarenessResponse")
    val awarenessResponse: String,
    @SerializedName("awarenessSeriousness")
    val awarenessSeriousness: String?,
    @SerializedName("awareness_type")
    val awarenessType: String,
    @SerializedName("ceiling_above_sea_level")
    val ceilingAboveSeaLevel: Int,
    @SerializedName("certainty")
    val certainty: String,
    @SerializedName("consequences")
    val consequences: String?,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("county")
    val county: List<String>,
    @SerializedName("description")
    val description: String,
    @SerializedName("event")
    val event: String,
    @SerializedName("eventAwarenessName")
    val eventAwarenessName: String,
    @SerializedName("eventEndingTime")
    val eventEndingTime: String?,
    @SerializedName("geographicDomain")
    val geographicDomain: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("instruction")
    val instruction: String,
    @SerializedName("municipality")
    val municipality: List<String>?,
    @SerializedName("resources")
    val resources: List<Resource>,
    @SerializedName("riskMatrixColor")
    val riskMatrixColor: String,
    @SerializedName("severity")
    val severity: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("triggerLevel")
    val triggerLevel: String?,
    @SerializedName("type")
    val type: String,
    @SerializedName("web")
    val web: String
)