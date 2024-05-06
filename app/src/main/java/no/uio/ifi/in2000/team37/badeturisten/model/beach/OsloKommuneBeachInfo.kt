package no.uio.ifi.in2000.team37.badeturisten.model.beach

import kotlinx.serialization.Serializable

@Serializable
data class OsloKommuneBeachInfo(
    val waterQuality: String?,
    val facilitiesInfo: String?,
    val imageUrl: String?,
)
