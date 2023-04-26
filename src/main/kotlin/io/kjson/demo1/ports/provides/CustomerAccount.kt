package io.kjson.demo1.ports.provides

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class CustomerAccount(
    val id: String,
    val name: String,
    val reference: UUID,
    val creationDate: Instant,
    val balance: BigDecimal,
)
