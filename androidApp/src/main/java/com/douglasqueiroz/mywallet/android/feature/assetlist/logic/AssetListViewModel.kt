package com.douglasqueiroz.mywallet.android.feature.assetlist.logic

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasqueiroz.mywallet.android.extension.format
import com.douglasqueiroz.mywallet.domain.dto.AssetDto
import kotlinx.coroutines.launch

abstract class AssetListViewModel: ViewModel() {

    var state: AssetListUIState by mutableStateOf(AssetListUIState())
        private set

    abstract suspend fun loadAssetList(): List<AssetDto>

    abstract fun getTitle(): String

    protected fun setupScreen() = viewModelScope.launch {

        val assetDtoList = loadAssetList()
        val assetUIItemList = showAssetList(assetDtoList)

        state = state.copy(
            type = getTitle(),
            assetList = assetUIItemList
        )
    }

    private fun showAssetList(assetDto: List<AssetDto>) = assetDto
        .sortedByDescending {
            it.total
        }
        .map {
        val name = if (it.code.isEmpty()) {
            it.name
        } else {
            "(${it.code}) ${it.name}"
        }

        val total = it.currency.symbol + it.total.format("#.####")

        AssetListUIItem(
            name = name,
            amount = it.amount.format(),
            total = total
        )
    }
}