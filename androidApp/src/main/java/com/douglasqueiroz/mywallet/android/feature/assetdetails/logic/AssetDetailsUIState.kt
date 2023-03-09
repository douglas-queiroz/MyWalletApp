package com.douglasqueiroz.mywallet.android.feature.assetdetails.logic

data class AssetDetailsUIState(
    val assetId: String = "",
    val name: String = "",
    val code: String = "",
    val quantity: String = "",
    val total: String = "",
    val transactionList: List<AssetDetailsUITransactionItem> = emptyList()
)
