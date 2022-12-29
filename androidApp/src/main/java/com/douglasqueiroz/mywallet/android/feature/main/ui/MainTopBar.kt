package com.douglasqueiroz.mywallet.android.feature.main.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import com.douglasqueiroz.mywallet.android.compose.MyWalletTopAppBar

@Composable
fun MainTopBar(
    onRefresh: () -> Unit
) {
    MyWalletTopAppBar(
        actions = {
            IconButton(onClick = { onRefresh() }) {
                Icon(Icons.Default.Refresh, "Refresh")
            }
        }
    )
}