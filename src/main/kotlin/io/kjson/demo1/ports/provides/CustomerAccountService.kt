package io.kjson.demo1.ports.provides

interface CustomerAccountService {
    suspend fun getAccount(id: String): CustomerAccount?
    suspend fun getAccountList(ids: List<String>): List<CustomerAccount>
    suspend fun getAccounts(ids: List<String>, consumer: suspend (CustomerAccount) -> Unit)
    suspend fun getAccountFlow(ids: List<String>, consumer: suspend (CustomerAccount) -> Unit)
    suspend fun postAccountFlow(ids: List<String>, consumer: suspend (CustomerAccount) -> Unit)
}
