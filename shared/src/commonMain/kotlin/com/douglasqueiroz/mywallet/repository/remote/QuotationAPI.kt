package com.douglasqueiroz.mywallet.repository.remote

import com.douglasqueiroz.mywallet.BuildKonfig
import com.douglasqueiroz.mywallet.repository.remote.response.QuotationAPIResponse
import io.ktor.client.request.*

internal class QuotationAPI: BaseApi() {

    companion object {
        private const val URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes"
        private const val KEY_PARAM = "x-rapidapi-key"
        private const val HOST_PARAM = "x-rapidapi-host"
        private const val HOST = "apidojo-yahoo-finance-v1.p.rapidapi.com"
        private const val REGION_PARAM = "region"
        private const val SYMBOLS_PARAM = "symbols"
    }

    suspend fun getQuotations(codes: List<String>) = httpClient.request<QuotationAPIResponse>(URL) {
        headers {
            append(KEY_PARAM, BuildKonfig.QUOTE_KEY)
            append(HOST_PARAM, HOST)
        }

        parameter(REGION_PARAM, "US")
        parameter(SYMBOLS_PARAM, codes.joinToString(","))
    }
}