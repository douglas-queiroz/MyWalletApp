package com.douglasqueiroz.mywallet.domain.dto

data class ActiveDto(
    val id: String,
    val name: String,
    val currency: String,
    val total: Double
)