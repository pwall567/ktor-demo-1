package io.kjson.demo1.app

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

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

    override suspend fun getAccountList(ids: List<String>): List<CustomerAccount> {
        val list = config.partyClient.getList(ids.joinToString("."))
        return list.map { it.toCustomerAccount() }
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

    override suspend fun postAccountFlow(ids: List<String>, consumer: suspend (CustomerAccount) -> Unit) {
        val flow = flow {
            for (id in ids) {
                delay(3000)
                emit(id)
            }
        }
        config.partyClient.postFlow(flow) {
            consumer(it.toCustomerAccount())
        }
    }

    companion object {

        fun Party.toCustomerAccount(): CustomerAccount = CustomerAccount(
            id = id,
            name = name,
            reference = reference,
            creationDate = creationDate,
            balance = balances[id] ?: BigDecimal.ZERO,
        )

        private val balances = mapOf(
            "1" to BigDecimal("1234.00"),
            "2" to BigDecimal("22222.34"),
            "3" to BigDecimal("0.00"),
            "4" to BigDecimal("5.10"),
        )

    }

}
