package io.kjson.demo1.adapters.provides

import io.ktor.server.application.ApplicationEnvironment

import io.kjson.demo1.ports.provides.Properties

class KtorProperties(private val environment: ApplicationEnvironment) : Properties {

    override fun get(string: String): String? {
        return environment.config.propertyOrNull(string)?.getString()
    }

}
