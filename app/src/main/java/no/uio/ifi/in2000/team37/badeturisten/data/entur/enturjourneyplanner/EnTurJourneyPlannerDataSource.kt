package no.uio.ifi.in2000.team37.badeturisten.data.entur.enturjourneyplanner

import io.ktor.client.HttpClient
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

class EnTurJourneyPlannerDataSource {
    private val client = HttpClient {
        defaultRequest {
            url("https://api.entur.io/journey-planner/v3/graphql")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header("IN2000-badeturisten", "study-application")
        }
        install(ContentNegotiation) {
            gson {}
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun getRute(id: String): Any {
        val graphQLQuery = """
        query MinQuery {
          stopPlace(id: "$id") {
            id
            name
            estimatedCalls(numberOfDepartures: 10) {
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

        try {
            val response: HttpResponse = client.post() {
                contentType(ContentType.Application.Json)
                body = requestBody
            }
            return response
        } catch (e: Exception) {
            e.printStackTrace()
            return "Feil under henting av data: ${e.message}"
        }

    }
}