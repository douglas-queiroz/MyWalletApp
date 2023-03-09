package com.douglasqueiroz.mywallet.android.feature.assetdetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.douglasqueiroz.mywallet.android.R
import com.douglasqueiroz.mywallet.android.compose.MyWalletTopAppBar
import com.douglasqueiroz.mywallet.android.feature.assetdetails.logic.AssetDetailsUIState
import com.douglasqueiroz.mywallet.android.feature.assetdetails.logic.AssetDetailsUITransactionItem

@Composable
fun AssetDetailsScreen(
    state: AssetDetailsUIState,
    onAddTransaction: (String) -> Unit = { _ -> },
    refresh: () -> Unit = { }
) {

    OnLifecycleEvent { owner, event ->
        // do stuff on event
        when (event) {
            Lifecycle.Event.ON_RESUME -> { refresh.invoke() }
            else                      -> { /* other stuff */ }
        }
    }

    MaterialTheme {

        Scaffold(
            topBar = { MyWalletTopAppBar() }
        ) {
            AssetDetailsContent(
                paddingValues = it,
                state = state,
                onAddTransaction = onAddTransaction
            )
        }
    }
}

@Composable
fun AssetDetailsContent(
    paddingValues: PaddingValues,
    state: AssetDetailsUIState,
    onAddTransaction: (String) -> Unit
) {

    Column(modifier = Modifier
        .padding(paddingValues)
    ) {
        LazyColumn(
            Modifier
                .padding(16.dp)
                .weight(1f)
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
                        text = item.quantity
                    )
                    Text(text = item.total)
                }

            }
        }
        
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                modifier = Modifier.weight(1f),
                onClick = { /*TODO*/ }
            ) {
                Text(text = stringResource(id = R.string.back_button))
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = { onAddTransaction(state.assetId) } )
            {
                Text(text = stringResource(id = R.string.add_transaction_button))
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

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}