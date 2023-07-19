package io.kjson.demo1.adapters.requires

import kotlinx.coroutines.flow.Flow

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode

import io.kjson.demo1.ports.requires.Party
import io.kjson.demo1.ports.requires.PartyClient
import io.kjson.ktor.receiveStreamJSON
import io.kjson.ktor.receiveStreamJSONLines
import net.pwall.log.getLogger

class PartyClientImpl(
    private val client: HttpClient,
) : PartyClient {

    override suspend fun getParty(id: String): Party {
        val response = client.get("$PARTY_SERVER_BASE_URI/party/single/$id")
        when (response.status) {
            HttpStatusCode.OK -> return response.body()
            else -> throw IllegalStateException("Something went wrong")
        }
    }

    override suspend fun getList(ids: String): List<Party> {
        val response = client.get("$PARTY_SERVER_BASE_URI/party/list/$ids")
        when (response.status) {
            HttpStatusCode.OK -> return response.body()
            else -> throw IllegalStateException("Something went wrong")
        }
    }

    override suspend fun getStream(ids: String, consumer: suspend (Party) -> Unit) {
        client.receiveStreamJSON<Party>("$PARTY_SERVER_BASE_URI/party/channel/$ids") {
            log.info { "Received ${it.id}" }
            consumer(it)
        }
    }

    override suspend fun getFlow(ids: String, consumer: suspend (Party) -> Unit) {
        client.receiveStreamJSON<Party>("$PARTY_SERVER_BASE_URI/party/flow/$ids") {
            log.info { "Received ${it.id}" }
            consumer(it)
        }
    }

    override suspend fun getFlowLines(ids: String, consumer: suspend (Party) -> Unit) {
        client.receiveStreamJSONLines<Party>("$PARTY_SERVER_BASE_URI/party/lines/$ids") {
            log.info { "Received ${it.id}" }
            consumer(it)
        }
    }

    override suspend fun postFlow(ids: Flow<String>, consumer: suspend (Party) -> Unit) {
        client.receiveStreamJSON<Party>(
            urlString = "$PARTY_SERVER_BASE_URI/party/post",
            method = HttpMethod.Post,
            body = ids,
        ) {
            log.info { "Received ${it.id}" }
            consumer(it)
        }
    }

    companion object {
        val log = getLogger()
        const val PARTY_SERVER_BASE_URI = "http://localhost:8102"
    }

}
