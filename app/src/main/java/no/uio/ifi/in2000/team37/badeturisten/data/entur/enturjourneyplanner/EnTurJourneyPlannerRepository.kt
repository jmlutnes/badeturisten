package no.uio.ifi.in2000.team37.badeturisten.data.entur.enturjourneyplanner

class EnTurJourneyPlannerRepository (val dataSource: EnTurJourneyPlannerDataSource) {
    suspend fun hentBussruterMedId(bussstasjonId: String): Any? {
        try {
            // Henter rutedata basert på busstasjonens ID
            val ruteData = dataSource.getRute(bussstasjonId)
            println(ruteData)
            return ruteData
        } catch (e: Exception) {
            println("En feil oppstod ved henting av bussruter: ${e.message}")
            return null
        }
    }
}