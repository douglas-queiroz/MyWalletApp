package com.douglasqueiroz.mywallet.android.feature.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.douglasqueiroz.mywallet.android.compose.AssetType
import com.douglasqueiroz.mywallet.android.feature.main.logic.ShareItem

@Composable
fun MainViewList(
    shareItemList: List<ShareItem>,
    total: String,
    onItemClick: (AssetType) -> Unit
) {

    LazyColumn(content = {

        itemsIndexed(shareItemList) { index, item ->

            if (index == 0) {
                MainHeader(total = total)
            }

            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
                    .clickable { onItemClick(item.assetType) }
            ) {

                Column(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(16.dp)
                ) {

                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier.align(Alignment.End)
                    ) {

                        Spacer(modifier = Modifier.weight(1f))

                        Text("Total: ${item.total} (${item.percentage})")
                    }
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun PreviewMainViewList() {
    val list = arrayListOf(
        ShareItem("Stock", "€100", "30%"),
        ShareItem("Fixed income", "€400", "50%"),
        ShareItem("Saves", "€300", "25%"),
        ShareItem("Gold", "€300", "25%"),
        ShareItem("Bitcoin", "€300", "25%"),
    )

    MainViewList(
        shareItemList = list,
        total = "R$ 100"
    ) {}
}