package com.douglasqueiroz.mywallet.android.feature.assetlist.logic

import com.douglasqueiroz.mywallet.domain.enum.FixedIncomeType
import com.douglasqueiroz.mywallet.domain.usecases.FetchFixedIncomeByTypeUseCase

class FixedIncomeAssetListViewModel(
    private val fixedIncomeType: FixedIncomeType,
    private val fetchFixedIncomeByTypeUseCase: FetchFixedIncomeByTypeUseCase
): AssetListViewModel() {

    init {
        setupScreen()
    }

    override suspend fun loadAssetList() = fetchFixedIncomeByTypeUseCase.execute(fixedIncomeType)

    override fun getTitle() = when(fixedIncomeType) {
        FixedIncomeType.SAVING_ACCOUNT -> "Saving Accounts"
    }
}