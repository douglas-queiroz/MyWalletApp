package com.douglasqueiroz.mywallet.repository.remote

import com.douglasqueiroz.mywallet.BuildKonfig
import com.douglasqueiroz.mywallet.repository.remote.response.*
import io.ktor.client.request.*

internal class MyWalletOldApi: BaseApi() {

    companion object {
        private const val CURRENCY_URL = "/currencies"
        private const val FIXED_INCOMES_URL = "/fixed_incomes"
        private const val SHARES_URL = "/shares"
        private const val TRANSACTIONS_URL = "/transactions"
        private const val CURRENCY_CONVERSION_URL = "/currency_conversions"
        private const val QUOTE_URL = "/quotes"
        private const val CURRENCY_QUOTE_URL = "/currency_quotations"
    }

    suspend fun getCurrencies() : List<CurrencyResponse> {
        return httpClient.get(getBaseUrl() + CURRENCY_URL)
    }

    suspend fun getFixedIncomes(): List<FixedIncomesResponse> {
        return httpClient.get(getBaseUrl() + FIXED_INCOMES_URL)
    }

    suspend fun getShares(): List<ShareResponse> {
        return httpClient.get(getBaseUrl() + SHARES_URL)
    }

    suspend fun getTransactions(): List<TransactionResponse> {
        return httpClient.get(getBaseUrl() + TRANSACTIONS_URL)
    }

    suspend fun getCurrencyConversion(): List<CurrencyConversionResponse> {
        return httpClient.get(getBaseUrl() + CURRENCY_CONVERSION_URL)
    }

    suspend fun getQuotes(): List<QuoteResponse> {
        return httpClient.get(getBaseUrl() + QUOTE_URL)
    }

    suspend fun getCurrencyQuotation(): List<CurrencyQuotationResponse> {
        return httpClient.get(getBaseUrl() + CURRENCY_QUOTE_URL)
    }

    private fun getBaseUrl() = BuildKonfig.OLD_API_BASE_URL
}