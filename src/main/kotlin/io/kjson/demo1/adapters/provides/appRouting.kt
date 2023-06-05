package io.kjson.demo1.adapters.provides

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.flow

import io.ktor.server.application.call
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

import io.kjson.demo1.ports.requires.Config
import io.kjson.ktor.respondStream
import io.kjson.mustache.util.Counter
import net.pwall.log.getLogger

@OptIn(ExperimentalCoroutinesApi::class)
fun Routing.appRouting(config: Config) {

    val log = getLogger()

    get("/customer/single/{id}") {
        val id = call.parameters["id"] ?: throw IllegalArgumentException("No id")
        log.info { "GET /customer/single/$id" }
        val account = config.customerAccountService.getAccount(id) ?:
                throw NotFoundException("Not found: $id")
        call.respond(account)
    }

    get("/customer/channel/{ids}") {
        val ids = call.parameters["ids"] ?: throw IllegalArgumentException("No ids")
        log.info { "GET /customer/channel/$ids" }
        val channel = call.application.produce {
            config.customerAccountService.getAccounts(ids.split('.')) {
                send(it)
            }
        }
        call.respond(channel)
    }

    get("/customer/flow/{ids}") {
        val ids = call.parameters["ids"] ?: throw IllegalArgumentException("No ids")
        log.info { "GET /customer/flow/$ids" }
        val flow = flow {
            config.customerAccountService.getAccountFlow(ids.split('.')) {
                emit(it)
            }
        }
        call.respond(flow)
    }

    get("/display/{ids}") {
        val ids = call.parameters["ids"] ?: throw IllegalArgumentException("No ids")
        log.info { "GET /display/$ids" }
        val count = Counter(units = "customer")
        val flow = flow {
            config.customerAccountService.getAccountFlow(ids.split('.')) {
                emit(it)
                count.increment()
            }
        }
        val mustacheContext = mapOf("list" to flow, "count" to count)
        call.respondStream {
            config.mustacheTemplate.coRender(mustacheContext, this)
        }
    }

    get("/display-post/{ids}") {
        val ids = call.parameters["ids"] ?: throw IllegalArgumentException("No ids")
        log.info { "GET /display-post/$ids" }
        val count = Counter(units = "customer")
        val flow = flow {
            config.customerAccountService.postAccountFlow(ids.split('.')) {
                emit(it)
                count.increment()
            }
        }
        val mustacheContext = mapOf("list" to flow, "count" to count)
        call.respondStream {
            config.mustacheTemplate.coRender(mustacheContext, this)
        }
    }

}
