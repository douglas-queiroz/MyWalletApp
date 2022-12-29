package com.douglasqueiroz.mywallet.android.feature.main.logic

data class MainStateView (
    val shareItemList: List<ShareItem> = emptyList(),
    val showLoadingProgress: Boolean = false,
    val total: String = "€ 0.00"
)
