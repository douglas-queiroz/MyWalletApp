package com.douglasqueiroz.mywallet.repository.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse (
    @SerialName("id")
    val id: Int,
    @SerialName("date")
    val date: String,
    @SerialName("price")
    val price: Float?,
    @SerialName("quantity")
    val quantity: Float?,
    @SerialName("total")
    val total: Float?,
    @SerialName("transactionable_id")
    val transactionableId: Int,
    @SerialName("transactionable_type")
    val transactionableType: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)