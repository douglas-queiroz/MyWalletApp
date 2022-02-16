package com.douglasqueiroz.mywallet.repository.remote

import com.douglasqueiroz.mywallet.repository.remote.response.CurrencyResponse
import com.douglasqueiroz.mywallet.repository.remote.response.FixedIncomesResponse
import com.douglasqueiroz.mywallet.repository.remote.response.ShareResponse
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

internal class MyWalletOldApi: BaseApi() {

    companion object {
        private const val BASE_URL = "http://192.168.1.100:3001"
        private const val CURRENCY_URL = "$BASE_URL/currencies"
        private const val FIXED_INCOMES_URL = "$BASE_URL/fixed_incomes"
        private const val SHARES_URL = "$BASE_URL/shares"
    }

    suspend fun getCurrencies() : List<CurrencyResponse> {
        return httpClient.get(CURRENCY_URL)
    }

    suspend fun getFixedIncomes(): List<FixedIncomesResponse> {
        return httpClient.get(FIXED_INCOMES_URL)
    }

    suspend fun getShares(): List<ShareResponse> {
        return httpClient.get(SHARES_URL)
    }
}