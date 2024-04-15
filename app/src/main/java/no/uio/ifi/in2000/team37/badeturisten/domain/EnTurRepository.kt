package no.uio.ifi.in2000.team37.badeturisten.domain

import no.uio.ifi.in2000.team37.badeturisten.data.entur.EnTurDataSource

interface EnTurRepository {
    val dataSource: EnTurDataSource
    suspend fun hentBussrute(navn: String): String?
}