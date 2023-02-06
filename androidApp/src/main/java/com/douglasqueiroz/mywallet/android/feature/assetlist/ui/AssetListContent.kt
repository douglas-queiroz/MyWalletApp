package com.douglasqueiroz.mywallet.android.feature.assetlist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.douglasqueiroz.mywallet.android.compose.MyWalletTopAppBar
import com.douglasqueiroz.mywallet.android.feature.assetlist.logic.AssetListUIState
import com.douglasqueiroz.mywallet.android.feature.assetlist.logic.AssetListUIItem

@Composable
fun AssetListContent(
    state: AssetListUIState,
    onClick: (String) -> Unit
) {

    MaterialTheme {
        Scaffold(
            topBar = { MyWalletTopAppBar() }
        ) {
            AssertList(
                padding = it,
                list = state.assetList,
                onClick = onClick
            )
        }
    }
}

@Composable
fun AssertList(padding: PaddingValues, list: List<AssetListUIItem>, onClick: (String) -> Unit) {
    LazyColumn(modifier = Modifier.padding(padding), content = {

        items(items = list) { item ->
            AssertCardItem(item = item, onClick = onClick)

        }
    })
}

@Composable
fun AssertCardItem(
    item: AssetListUIItem,
    onClick: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .clickable { onClick(item.assetId) }
    ) {

        Box(
            modifier = Modifier
                .height(100.dp)
                .padding(16.dp)
        ) {

            Column {

                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(text = "Qtd: ${item.amount}x")

                Row(
                    modifier = Modifier.align(Alignment.End)
                ) {

                    Spacer(modifier = Modifier.weight(1f))

                    Text("Total: ${item.total}")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AssetListContentPreview() {

    val state = AssetListUIState(
        type = "Fixed Income",
        assetList = listOf(
            AssetListUIItem(
                assetId = "asset_id",
                name = "(IPCA+10%) Tesouro Direto IPCA Mais 10 porcento",
                amount = "100",
                total = "R$ 20.000,00"
            ),
            AssetListUIItem(
                assetId = "asset_id",
                name = "Tesouro Direto IPCA",
                amount = "100",
                total = "R$ 20.000,00"
            ),
            AssetListUIItem(
                assetId = "asset_id",
                name = "Tesouro Direto IPCA",
                amount = "100",
                total = "R$ 20.000,00"
            ),
            AssetListUIItem(
                assetId = "asset_id",
                name = "Tesouro Direto IPCA",
                amount = "100",
                total = "R$ 20.000,00"
            ),
            AssetListUIItem(
                assetId = "asset_id",
                name = "Tesouro Direto IPCA",
                amount = "100",
                total = "R$ 20.000,00"
            ),
            AssetListUIItem(
                assetId = "asset_id",
                name = "Tesouro Direto IPCA",
                amount = "100",
                total = "R$ 20.000,00"
            ),
            AssetListUIItem(
                assetId = "asset_id",
                name = "Tesouro Direto IPCA",
                amount = "100",
                total = "R$ 20.000,00"
            )
        )
    )


    AssetListContent(state, onClick = {})
}