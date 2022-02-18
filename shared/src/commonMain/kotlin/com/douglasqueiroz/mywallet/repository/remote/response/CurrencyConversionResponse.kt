package com.douglasqueiroz.mywallet.repository.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyConversionResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("currency_from_id")
    val currencyFromId: Int,
    @SerialName("currency_to_id")
    val currencyToId: Int,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)
