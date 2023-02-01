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
import com.douglasqueiroz.mywallet.android.compose.AssetType
import com.douglasqueiroz.mywallet.android.compose.MyWalletLoading
import com.douglasqueiroz.mywallet.android.feature.main.logic.MainStateView
import com.douglasqueiroz.mywallet.android.feature.main.logic.MainViewModel
import com.douglasqueiroz.mywallet.android.feature.main.logic.ShareItem
import com.douglasqueiroz.mywallet.domain.dto.OverallReportDto
import com.douglasqueiroz.mywallet.domain.usecases.CalculateOverallReportUseCase
import com.douglasqueiroz.mywallet.domain.usecases.CollectQuotationsUseCase

@Composable
fun MainContent(
    state: MainStateView,
    onRefreshBtnClick: () -> Unit,
    onAssetClick: (AssetType) -> Unit
) {

    MaterialTheme {

        Scaffold(
            topBar = { MainTopBar(onRefreshBtnClick) },
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
                        shareItemList = state.shareItemList,
                        total = state.total,
                        onItemClick = onAssetClick
                    )
                }

                MyWalletLoading(show = state.showLoadingProgress)
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainContent() {
    val state =  MainStateView(
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
    )

    MainContent(
        state = state,
        onRefreshBtnClick = {},
        onAssetClick = {}
    )
}