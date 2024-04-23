package no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import no.uio.ifi.in2000.team37.badeturisten.data.dependencyinjection.EnTurHttpClient
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.jsontokotlinenturjourneyplanner.jsontokotlinenturjourneyplanner

class EnTurJourneyPlannerDataSource(@EnTurHttpClient private val client: HttpClient) {
    /**
     * Send in a stopPlace ID to receive the transportation related to the the stop place.
     * Sends a request for the JourneyPlanner API with the ID.
     */
    @OptIn(InternalAPI::class)
    suspend fun getRute(id: String): jsontokotlinenturjourneyplanner {
        val graphQLQuery = """
        query MinQuery {
          stopPlace(id: "$id") {
            id
            name
            estimatedCalls(numberOfDepartures: 2) {
              expectedDepartureTime
              destinationDisplay {
                frontText
              }
              serviceJourney {
                journeyPattern {
                  line {
                    id
                    name
                    transportMode
                    publicCode
                  }
                }
              }
            }
          }
        }
    """.trimIndent()
        val requestBody = buildJsonObject {
            put("query", graphQLQuery)
        }.toString()

        val response: HttpResponse = client.post() {
                contentType(ContentType.Application.Json)
                body = requestBody
        }
        return response.body<jsontokotlinenturjourneyplanner>()
    }
}