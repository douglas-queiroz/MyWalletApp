package com.douglasqueiroz.mywallet.domain.dto

data class ActiveDto(
    val id: String,
    val name: String,
    val symbol: String,
    val currency: String,
    val total: Double,
    val transactions: List<TransactionDto>
)

