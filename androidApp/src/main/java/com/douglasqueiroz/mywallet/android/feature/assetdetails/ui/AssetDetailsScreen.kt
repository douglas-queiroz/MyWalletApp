package com.douglasqueiroz.mywallet.android.feature.assetdetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.douglasqueiroz.mywallet.android.R
import com.douglasqueiroz.mywallet.android.compose.MyWalletTopAppBar
import com.douglasqueiroz.mywallet.android.feature.assetdetails.logic.AssetDetailsUIState
import com.douglasqueiroz.mywallet.android.feature.assetdetails.logic.AssetDetailsUITransactionItem

@Composable
fun AssetDetailsScreen(state: AssetDetailsUIState) {

    MaterialTheme {

        Scaffold(
            topBar = { MyWalletTopAppBar() }
        ) {
            AssetDetailsContent(
                paddingValues = it,
                state = state
            )
        }
    }
}

@Composable
fun AssetDetailsContent(
    paddingValues: PaddingValues,
    state: AssetDetailsUIState
) {

    LazyColumn(
        Modifier
            .padding(paddingValues)
            .padding(16.dp)
    ) {

        itemsIndexed(state.transactionList) { index, item ->

            if (index == 0) {
                AssetDetailsHeader(
                    name = state.name,
                    code = state.code,
                    quantity = state.quantity,
                    total = state.total
                )
            }
                
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
            ) {
                Text(text = item.date)
                Spacer(modifier = Modifier.weight(1.0f))
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = item.quantity)
                Text(text = item.total)
            }

        }
    }
}

@Composable
fun AssetDetailsHeader(
    name: String,
    code: String,
    quantity: String,
    total: String
) {
    Text(
        text = stringResource(
            id = R.string.asset_details_name,
            formatArgs = arrayOf(name)
        )
    )
    Text(
        text = stringResource(
            id = R.string.asset_details_code,
            formatArgs = arrayOf(code)
        )
    )
    Text(
        text = stringResource(
            id = R.string.asset_details_amount,
            formatArgs = arrayOf(quantity)
        )
    )
    Text(
        modifier = Modifier.padding(bottom = 16.dp),
        text = stringResource(
            id = R.string.asset_details_total,
            formatArgs = arrayOf(total)
        )
    )
}

@Composable
@Preview(showBackground = true)
fun AssetDetailsScreen_Preview() {

    val transaction = AssetDetailsUITransactionItem(
        date = "01/01/2021",
        quantity = "10x",
        total = "R$ 1.000,00"
    )

    AssetDetailsScreen(
        state = AssetDetailsUIState(
            name = "Ambeve",
            code = "ABEV3.SA",
            quantity = "303x",
            total = "R$3,945.06",
            transactionList = listOf(
                transaction,
                transaction,
                transaction,
                transaction
            )
        )
    )
}