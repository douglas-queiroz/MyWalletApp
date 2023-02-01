package com.douglasqueiroz.mywallet.android.feature.main.logic

import com.douglasqueiroz.mywallet.android.compose.AssetType

data class ShareItem(
    val name: String = "Mo name",
    val total: String = "0,00",
    val percentage: String = "0%",
    val assetType: AssetType = AssetType.STOCK
)
