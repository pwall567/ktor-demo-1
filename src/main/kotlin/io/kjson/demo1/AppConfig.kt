package io.kjson.demo1

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing

import io.kstuff.log.getLogger

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
import io.kjson.mustache.Template
import io.kjson.mustache.parser.Parser

object AppConfig : Config {

    override val properties: Properties
        get() = ktorProperties

    private lateinit var ktorProperties: KtorProperties

    override val partyClient: PartyClient = PartyClientImpl(ClientFactory.createHttpClient())

    override val customerAccountService: CustomerAccountService = CustomerAccountServiceImpl(AppConfig)

    private val resourceURL = AppConfig::class.java.getResource("/templates") ?:
            throw RuntimeException("Templates not found")

    override val mustacheParser = Parser(resourceURL)
    override val mustacheTemplate: Template = mustacheParser.parseByName("customers")

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
