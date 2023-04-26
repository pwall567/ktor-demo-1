package io.kjson.demo1

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing

import io.kjson.demo1.adapters.provides.KtorProperties
import io.kjson.demo1.adapters.provides.appRouting
import io.kjson.demo1.adapters.requires.ClientFactory
import io.kjson.demo1.adapters.requires.PartyClientImpl
import io.kjson.demo1.app.CustomerAccountServiceImpl
import io.kjson.demo1.ports.provides.CustomerAccountService
import io.kjson.demo1.ports.provides.Properties
import io.kjson.demo1.ports.requires.Config
import io.kjson.demo1.ports.requires.PartyClient
import io.kjson.ktor.kjson
import net.pwall.log.getLogger

object AppConfig : Config {

    override val properties: Properties
        get() = ktorProperties

    private lateinit var ktorProperties: KtorProperties

    override val partyClient: PartyClient = PartyClientImpl(ClientFactory.createHttpClient())

    override val customerAccountService: CustomerAccountService = CustomerAccountServiceImpl(AppConfig)

    val log = getLogger()

    fun Application.module() {

        ktorProperties = KtorProperties(environment)

        install(ContentNegotiation) {
            kjson {
                streamOutput = true
            }
        }

        routing {
            appRouting(AppConfig)
        }

        log.info { properties["app.startMessage"] ?: "Starting..." }

    }

}
