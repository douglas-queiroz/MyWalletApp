package com.douglasqueiroz.mywallet.android.feature.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.douglasqueiroz.mywallet.android.compose.MyWalletLoading
import com.douglasqueiroz.mywallet.android.feature.main.logic.MainStateView
import com.douglasqueiroz.mywallet.android.feature.main.logic.MainViewModel
import com.douglasqueiroz.mywallet.android.feature.main.logic.ShareItem
import com.douglasqueiroz.mywallet.domain.dto.OverallReportDto
import com.douglasqueiroz.mywallet.domain.usecases.CalculateOverallReportUseCase
import com.douglasqueiroz.mywallet.domain.usecases.CollectQuotationsUseCase

@Composable
fun MainContent(
    viewModel: MainViewModel
) {

    val lazyListState = rememberLazyListState()

    MaterialTheme {

        Scaffold(
            topBar = { MainTopBar(viewModel::refresh) },
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MainViewList(
                        shareItemList = viewModel.state.shareItemList,
                        total = viewModel.state.total
                    )
                }

                MyWalletLoading(show = viewModel.state.showLoadingProgress)
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainContent() {
    val viewModel = MainViewModel(
        initialStateView = MainStateView(
            shareItemList = arrayListOf(
                ShareItem("Stock", "€100", "30%"),
                ShareItem("Fixed income", "€400", "50%"),
                ShareItem("Saves", "€300", "25%"),
                ShareItem("Saves", "€300", "25%"),
                ShareItem("Saves", "€300", "25%"),
                ShareItem("Saves", "€300", "25%"),
                ShareItem("Saves", "€300", "25%"),
            ),
            total = "€100.00"
        ),
        loadOverallReportUseCase = object : CalculateOverallReportUseCase {
            override suspend fun execute(): List<OverallReportDto> {
                TODO("Not yet implemented")
            }
        },
        collectQuotationsUseCase = object: CollectQuotationsUseCase {
            override suspend fun execute() {
                TODO("Not yet implemented")
            }
        }
    )

    MainContent(viewModel)
}