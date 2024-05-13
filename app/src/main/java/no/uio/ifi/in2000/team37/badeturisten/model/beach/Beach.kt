package no.uio.ifi.in2000.team37.badeturisten.model.beach

import kotlinx.serialization.Serializable
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Pos

@Serializable
data class Beach(
    val name: String,
    val pos: Pos,
    var waterTemp: Double?,
)