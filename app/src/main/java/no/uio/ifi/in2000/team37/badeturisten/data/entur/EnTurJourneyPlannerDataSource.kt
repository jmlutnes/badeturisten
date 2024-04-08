package no.uio.ifi.in2000.team37.badeturisten.data.entur

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import no.uio.ifi.in2000.team37.badeturisten.data.entur.jsontokotlinenturjourneyplanner.EstimatedCall

class EnTurJourneyPlannerDataSource {
    private val client = HttpClient {
        defaultRequest {
            url("https://api.entur.io/journey-planner/v3/graphql")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header("ET-Client-Name", "company-application")
        // Husk å erstatte med din faktiske klientinformasjon
        }
        install(ContentNegotiation) {
            gson{}
        }
    }
    @OptIn(InternalAPI::class)
    suspend fun getRute(id: String): String {
        val headers = mapOf(
            "Content-Type" to "application/json",
            "ET-Client-Name" to "company-application" // Erstatt 'company-application' med din faktiske klientinformasjon
        )

        val graphQLQuery = """
        {
            "query": "{ stopPlace(id: \\"$id\\") { id name } }"
        }
    """.trimIndent()


        val response: HttpResponse = client.post() {
            contentType(ContentType.Application.Json)
            body = graphQLQuery
        }

        return response.body<String>()
    }
}