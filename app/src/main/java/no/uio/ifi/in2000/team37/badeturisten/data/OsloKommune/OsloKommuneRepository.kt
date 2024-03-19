package no.uio.ifi.in2000.team37.badeturisten.data.OsloKommune

class OsloKommuneRepository (private val datasource: OsloKommuneDatasource) {
    //Henter naermeste badested basert på soek, og gjoer nytt soek med oppdatert lokasjon til badested
    suspend fun getClass(lat: Double?, lon: Double?): jsontokotlin_kommune {
        val item = datasource.getData(lat, lon)
        val feat = item.meta.inputs.items.firstOrNull()?.value
        //println("Gammel lokasjon: $item")
        if (feat is Value) {
            //val lon = feat.longitude
            //val lat = feat.latitude
            //val item = getRight(lat, lon)
            //println("Ny lokasjon:$item")
            return item
        }
        return item
    }


    suspend fun getRight(lat: Double, lon: Double): jsontokotlin_kommune {
        val item = datasource.getData(lat, lon)
        return item
    }

    fun extractUrl(inputString: String): String {
        val streng = """href="(.*?)"""".toRegex()
        val matchResult = streng.find(inputString)
        return matchResult?.groups?.get(1)?.value ?: ""
    }

    suspend fun skrapUrl(input: String): BadevannsInfo {
        val item = datasource.skrapUrl(input)
        return item
    }

    suspend fun getVannkvalitet(lat: Double?, lon: Double?): BadevannsInfo? {
        val nettsideUrl: String? =
           getClass(lat, lon).data.geoJson.features.firstOrNull()?.properties?.popupContent
        println("old: $nettsideUrl")
        val nynettsideUrl = nettsideUrl?.let { extractUrl(it) }
        if (nynettsideUrl != null) {
            println("Ekstrahert URL: $nynettsideUrl")
        } else {
            println("Ingen URL funnet.")
        }
        val skrapOsloKommune = nynettsideUrl?.let { skrapUrl(it) }
        //println("Skrapt innhold: $skrapOsloKommune")
        return skrapOsloKommune
    }

}
