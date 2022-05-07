package com.douglasqueiroz.mywallet.domain.dto

data class AssetDto(
    val id: String,
    val name: String,
    val code: String,
    val currency: CurrencyDto,
    val total: Double,
    val transactions: List<TransactionDto> = listOf()
)

