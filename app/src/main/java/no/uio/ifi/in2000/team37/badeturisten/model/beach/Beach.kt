package no.uio.ifi.in2000.team37.badeturisten.model.beach
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Pos

data class Beach(
    val name: String,
    val pos: Pos,
    var waterTemp: Double?,
    val favorite: Boolean
    /*
    val badevannInfo: BadevannInfo
    val airTemp: Double?
    val img: String
    */

)