package com.douglasqueiroz.mywallet.repository.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuoteResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("date")
    val date: String,
    @SerialName("price")
    val price: Float,
    @SerialName("share_id")
    val shareId: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updateAt: String
)