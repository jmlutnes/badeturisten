package no.uio.ifi.in2000.team37.badeturisten.domain

import no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile.Bussrute

interface EnTurJourneyPlannerRepository {
    // definer metodene som trengs
    suspend fun hentBussruterMedId(bussstasjonId: String): MutableList<Bussrute>?
}