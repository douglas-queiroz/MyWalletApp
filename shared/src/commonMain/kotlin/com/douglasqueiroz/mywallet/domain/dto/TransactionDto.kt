package com.douglasqueiroz.mywallet.domain.dto

data class TransactionDto (
    val id: String,
    val date: String,
    val quantity: Double,
    val price: Double,
    val total: Double
)