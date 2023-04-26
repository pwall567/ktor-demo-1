package io.kjson.demo1.ports.requires

interface PartyClient {
    suspend fun getParty(id: String): Party
    suspend fun getStream(ids: String, consumer: suspend (Party) -> Unit)
    suspend fun getFlow(ids: String, consumer: suspend (Party) -> Unit)
}
