package io.kjson.demo1.adapters.requires

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation

import io.kjson.ktor.kjson

object ClientFactory {

    fun createHttpClient() = HttpClient(CIO) {
        install(ContentNegotiation) {
            kjson {
                streamOutput = true
            }
        }
    }

}
