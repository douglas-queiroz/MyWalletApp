package com.douglasqueiroz.mywallet.domain.dto

import kotlinx.datetime.LocalDateTime

data class CurrencyDto(
    val id: String,
    val name: String,
    val symbol: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)