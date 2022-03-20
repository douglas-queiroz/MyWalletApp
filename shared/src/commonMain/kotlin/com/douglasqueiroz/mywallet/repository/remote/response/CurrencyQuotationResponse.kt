package com.douglasqueiroz.mywallet.repository.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyQuotationResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("currency_conversion_id")
    val currencyConversionId: Int,
    @SerialName("date")
    val date: String,
    @SerialName("price")
    val price: Float,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updateAt: String
)
