package io.kjson.demo1.ports.requires

import io.kjson.demo1.ports.provides.CustomerAccountService
import io.kjson.demo1.ports.provides.Properties
import io.kjson.mustache.Template
import io.kjson.mustache.parser.Parser

interface Config {
    val properties: Properties
    val partyClient: PartyClient
    val customerAccountService: CustomerAccountService
    val mustacheParser: Parser
    val mustacheTemplate: Template
}
