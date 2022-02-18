package com.douglasqueiroz.mywallet.repository.remote

import com.douglasqueiroz.mywallet.repository.remote.response.*
import io.ktor.client.request.*

internal class MyWalletOldApi: BaseApi() {

    companion object {
        private const val BASE_URL = "http://192.168.1.100:3001"
        private const val CURRENCY_URL = "$BASE_URL/currencies"
        private const val FIXED_INCOMES_URL = "$BASE_URL/fixed_incomes"
        private const val SHARES_URL = "$BASE_URL/shares"
        private const val TRANSACTIONS_URL = "$BASE_URL/transactions"
        private const val CURRENCY_CONVERSION_URL = "$BASE_URL/currency_conversions"
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

    suspend fun getTransactions(): List<TransactionResponse> {
        return httpClient.get(TRANSACTIONS_URL)
    }

    suspend fun getCurrencyConversion(): List<CurrencyConversionResponse> {
        return httpClient.get(CURRENCY_CONVERSION_URL)
    }
}