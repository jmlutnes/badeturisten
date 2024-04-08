package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.jsontokotlinenturgeocoder


import com.google.gson.annotations.SerializedName

data class Properties(
    @SerializedName("accuracy")
    val accuracy: String,
    @SerializedName("category")
    val category: List<String>,
    @SerializedName("country_a")
    val countryA: String,
    @SerializedName("county")
    val county: String,
    @SerializedName("county_gid")
    val countyGid: String,
    @SerializedName("gid")
    val gid: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("layer")
    val layer: String,
    @SerializedName("locality")
    val locality: String,
    @SerializedName("locality_gid")
    val localityGid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("source_id")
    val sourceId: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("tariff_zones")
    val tariffZones: List<String>
)