package no.uio.ifi.in2000.team37.badeturisten.domain

interface EnTurRepository {

    /**
     * Receives a location name and return the nearest stop based on the name
     */
    suspend fun hentBussrute(navn: String): String?
}