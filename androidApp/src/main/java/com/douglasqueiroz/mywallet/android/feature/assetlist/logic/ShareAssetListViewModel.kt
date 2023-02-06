package com.douglasqueiroz.mywallet.android.feature.assetlist.logic

import com.douglasqueiroz.mywallet.domain.enum.ShareType
import com.douglasqueiroz.mywallet.domain.usecases.FetchShareByTypeUseCase

class ShareAssetListViewModel(
    private val shareType: ShareType,
    private val fetchShareByTypeUseCase: FetchShareByTypeUseCase
): AssetListViewModel() {

    init {
        setupScreen()
    }

    override suspend fun loadAssetList() = fetchShareByTypeUseCase.execute(shareType)

    override fun getTitle() = when(shareType) {
        ShareType.Stock -> "Stocks"
        ShareType.REIT -> "REITs"
        ShareType.GOLD -> "Gold"
        ShareType.BITCOIN -> "Bitcoin"
    }
}