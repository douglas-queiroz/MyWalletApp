package com.douglasqueiroz.mywallet.repository.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuotationAPIResponse (
    @SerialName("quoteResponse")
    val quoteResponse: QuotationQuoteResponse
)

@Serializable
data class QuotationQuoteResponse (
    @SerialName("result")
    val quotationResultResponse: List<QuotationResponse>
)

@Serializable
data class QuotationResponse (
    @SerialName("symbol")
    val symbol: String,

    @SerialName("regularMarketTime")
    val date: Long,

    @SerialName("regularMarketPrice")
    val price: Double
)