package com.douglasqueiroz.mywallet.android.feature.assetlist.logic

data class AssetListUIState(
    val type: String = "",
    val assetList: List<AssetListUIItem> = emptyList()
)
