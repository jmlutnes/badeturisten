package no.uio.ifi.in2000.team37.badeturisten.domain

interface EnTurRepository {
    suspend fun hentBussrute(navn: String): String?
}