package com.douglasqueiroz.mywallet.android.feature.main.logic

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasqueiroz.mywallet.android.extension.format
import com.douglasqueiroz.mywallet.domain.usecases.CalculateOverallReportUseCase
import com.douglasqueiroz.mywallet.domain.usecases.CollectQuotationsUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    initialStateView: MainStateView = MainStateView(),
    private val loadOverallReportUseCase: CalculateOverallReportUseCase,
    private val collectQuotationsUseCase: CollectQuotationsUseCase
): ViewModel() {

    var state: MainStateView by mutableStateOf(MainStateView())
        private set

    init {
        state = initialStateView
    }

    fun loadReport() = viewModelScope.launch {

        state = state.copy(showLoadingProgress = true)

        val reportItems = loadOverallReportUseCase
            .execute()

        val total = reportItems.sumOf { it.total }.format()

        val itemList = reportItems.map {
                val formattedTotal = it.total.format()
                val percentage = it.percentage.toInt()
                ShareItem(
                    name = it.name,
                    total = "R$ $formattedTotal",
                    percentage = "$percentage%"
                )
            }

        state = state.copy(
            shareItemList = itemList,
            showLoadingProgress = false,
            total = "R$ $total"
        )
    }

    fun refresh() {
        viewModelScope.launch {
            state = state.copy(showLoadingProgress = true)
            collectQuotationsUseCase.execute()
            loadReport()
        }
    }
}