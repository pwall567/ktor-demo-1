package io.kjson.demo1.ports.requires

import kotlinx.coroutines.flow.Flow

interface PartyClient {
    suspend fun getParty(id: String): Party
    suspend fun getList(ids: String): List<Party>
    suspend fun getStream(ids: String, consumer: suspend (Party) -> Unit)
    suspend fun getFlow(ids: String, consumer: suspend (Party) -> Unit)
    suspend fun getFlowLines(ids: String, consumer: suspend (Party) -> Unit)
    suspend fun postFlow(ids: Flow<String>, consumer: suspend (Party) -> Unit)
}
