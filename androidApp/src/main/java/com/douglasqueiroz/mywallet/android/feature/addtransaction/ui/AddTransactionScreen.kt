package com.douglasqueiroz.mywallet.android.feature.addtransaction.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.douglasqueiroz.mywallet.android.compose.MyWalletTopAppBar
import com.douglasqueiroz.mywallet.android.feature.addtransaction.logic.AddTransactionEvent
import com.douglasqueiroz.mywallet.android.feature.addtransaction.logic.AddTransactionViewState
import com.douglasqueiroz.mywallet.android.feature.assetdetails.ui.AssetDetailsContent

@Composable
fun AddTransactionScreen(
    state: AddTransactionViewState,
    onEvent: (AddTransactionEvent) -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = {

        },
        buttons = {
            Card(
                shape = RoundedCornerShape(15.dp),
                elevation = 10.dp
            ) {
                AddTransactionMain(
                    state = state,
                    onEvent = onEvent
                )
            }
        }
    )
}