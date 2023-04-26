package io.kjson.demo1.app

import java.math.BigDecimal

import io.kjson.demo1.ports.provides.CustomerAccount
import io.kjson.demo1.ports.provides.CustomerAccountService
import io.kjson.demo1.ports.requires.Config
import io.kjson.demo1.ports.requires.Party

class CustomerAccountServiceImpl(private val config: Config) : CustomerAccountService {

    override suspend fun getAccount(id: String): CustomerAccount {
        val party = config.partyClient.getParty(id)
        return party.toCustomerAccount()
    }

    override suspend fun getAccounts(ids: List<String>, consumer: suspend (CustomerAccount) -> Unit) {
        config.partyClient.getStream(ids.joinToString(".")) {
            consumer(it.toCustomerAccount())
        }
    }

    override suspend fun getAccountFlow(ids: List<String>, consumer: suspend (CustomerAccount) -> Unit) {
        config.partyClient.getFlow(ids.joinToString(".")) {
            consumer(it.toCustomerAccount())
        }
    }

    companion object {

        fun Party.toCustomerAccount(): CustomerAccount = CustomerAccount(
            id = id,
            name = name,
            reference = reference,
            creationDate = creationDate,
            balance = BigDecimal.ZERO,
        )

    }

}
