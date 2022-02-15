package com.douglasqueiroz.mywallet.repository.remote

import com.douglasqueiroz.mywallet.repository.remote.response.CurrencyResponse
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

internal class CurrencyApi: BaseApi() {

    companion object {
        const val BASE_URL = "http://192.168.1.100:3001/currencies"
    }

    suspend fun getCurrencies() : List<CurrencyResponse> {
        return httpClient.get(BASE_URL)
    }
}