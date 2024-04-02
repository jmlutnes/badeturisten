package no.uio.ifi.in2000.team37.badeturisten.data.OsloKommune

import android.util.Log
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Pos
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

class OsloKommuneRepository (private val datasource: OsloKommuneDatasource) {
    val liste: MutableList<Beach> = mutableListOf<Beach>()

    //Henter naermeste badested basert på soek, og gjoer nytt soek med oppdatert lokasjon til badested
    suspend fun getClass(lat: Double?, lon: Double?): jsontokotlin_kommune {
        val item = datasource.getData(lat, lon)
        val feat = item.meta.inputs.items.firstOrNull()?.value
        //println("Gammel lokasjon: $item")
        if (feat is Value) {
            val lon = feat.longitude
            val lat = feat.latitude
            val item = getRight(lat, lon)
            println("Ny lokasjon:$item")
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


    suspend fun getVannkvalitetLoc(lat: Double?, lon: Double?): BadevannsInfo? {
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


    //NYE METODER
    suspend fun getBadeplasser(lat: Double?, lon: Double?): List<Feature> {
        val item = datasource.getData(lon, lat)
        val feat = item.data.geoJson.features
        return feat
    }

    fun extractBeachFromHTML(html: String): String? {
        val regex = Regex("<a[^>]*>([^<]*)</a>")
        val matchResult = regex.find(html)
        return matchResult?.groups?.get(1)?.value
    }

    suspend fun finnNettside(navn: String): BadevannsInfo? {
        val features = getBadeplasser(59.91, 10.74)
        println("Navn:$navn")
        features.forEach { feature ->
            val beachNameNotConverted: String = feature.properties.popupContent.toString()
            val beachNameConverted: String? = extractBeachFromHTML(beachNameNotConverted.toString())

            println(beachNameConverted)
            if (beachNameConverted != null) {
                if (beachNameConverted.contains(navn)) {
                    //Fant stranden
                    val url = extractUrl(beachNameNotConverted)
                    val badeinfo = skrapUrl(url)
                    return badeinfo
                }
            }
        }
            println("ingen badeinfo funnet")
            return null
    }

        //METODE SOM HENTER ALLE BADESTEDER PÅ OSLO KOMMUNE (MED LOCATION) I LISTE
        suspend fun makeBeaches(lon: Double, lat: Double): List<Beach> {
            return try {
                //Liste som skal ha alle badestedene

                //Liste med Alle Features
                val features = getBadeplasser(lon, lat)

                //Itterer og hent koordinater og navn
                //geometry -> type -> Coordinates = Koordinater
                //properties -> popupContent -> bakers på streng før </a></h2
                println(features)
                features.forEach { feature ->
                    //Henter navn
                    val beachNameNotConverted: String? = feature.properties.popupContent
                    val beachNameConverted: String? =
                        extractBeachFromHTML(beachNameNotConverted.toString())

                    //Henter location
                    val location = feature.geometry.coordinates
                    val lon: String = location.get(0).toString()
                    val lat: String = location.get(1).toString()
                    //println(location)
                    //println(beachNameConverted)
                    val posisjon: Pos = Pos(lat, lon)
                    // lager strand objekter og legger til i liste
                    liste.add(Beach(beachNameConverted.toString(), posisjon, null))
                }
                //LAGER DATAKLASSE MED ALLE BEACHES
                return liste

            } catch (e: Exception) {
                Log.d("Oslo Kommune repository", "failed to make beaches")
                Log.e("Oslo Kommune repos", e.message.toString())
                emptyList<Beach>()
            }
        }

        suspend fun getBeach(beachName: String): Beach? {
            //METODE FOR AA HENTE EN STRAND BASERT PAA LOC ELLER NAVN?
            //val observationsFromDataSource = datasource.getData(59.91, 10.74)
            var beachlist: List<Beach> = makeBeaches(59.91, 10.74)
            beachlist = beachlist.filter { beach -> beach.name == beachName }
            return beachlist.firstOrNull()
        }
    }





