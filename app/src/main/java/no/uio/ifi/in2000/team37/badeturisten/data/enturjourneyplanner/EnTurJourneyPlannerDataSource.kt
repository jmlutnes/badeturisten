package no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.jsontokotlinenturjourneyplanner.jsontokotlinenturjourneyplanner

class EnTurJourneyPlannerDataSource {
    private val client = HttpClient {
        defaultRequest {
            url("https://api.entur.io/journey-planner/v3/graphql")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header("ET-Client-Name", "in2000study-application")
        }
        install(ContentNegotiation) {
            gson {}
        }
    }

    //Sender spoerring til EnTurJourneyPlanner or aa faa stopp basert paa id
    //Hentet fra enTur sin GeoCoder
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