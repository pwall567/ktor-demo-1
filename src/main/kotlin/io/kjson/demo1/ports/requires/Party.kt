package io.kjson.demo1.ports.requires

import java.time.Instant
import java.util.UUID

data class Party(val id: String, val name: String, val reference: UUID, val creationDate: Instant)
