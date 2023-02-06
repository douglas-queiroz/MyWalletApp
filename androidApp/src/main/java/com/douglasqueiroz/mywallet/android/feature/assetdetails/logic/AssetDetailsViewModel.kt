package com.douglasqueiroz.mywallet.android.feature.assetdetails.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasqueiroz.mywallet.android.extension.format
import com.douglasqueiroz.mywallet.domain.usecases.GetAssetUseCase
import kotlinx.coroutines.launch

class AssetDetailsViewModel(
    private val assetId: String,
    private val getAssetUseCase: GetAssetUseCase
): ViewModel() {

    var state: AssetDetailsUIState by mutableStateOf(AssetDetailsUIState())
        private set

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        val assetDto = getAssetUseCase.execute(assetId) ?: return@launch
        state = state.copy(
            name = assetDto.name,
            code = assetDto.code,
            total = assetDto.total.format(),
            transactionList = assetDto.transactions.map {
                AssetDetailsUITransactionItem(
                    date = it.date,
                    quantity = "${it.quantity}x",
                    total = "${assetDto.currency.symbol} ${it.total.format()}"
                )
            }
        )

    }
}