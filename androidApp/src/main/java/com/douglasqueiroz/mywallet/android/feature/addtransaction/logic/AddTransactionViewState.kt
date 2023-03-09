package com.douglasqueiroz.mywallet.android.feature.addtransaction.logic

data class AddTransactionViewState(
    val quantity: String = "",
    val quantityErrorMsg: String? = null,
    val price: String = "",
    val priceErrorMsg: String? = null,
    val total: String = "",
    val totalErrorMsg: String? = null,
    val date: String = "",
    val dateErrorMsg: String? = null
)
