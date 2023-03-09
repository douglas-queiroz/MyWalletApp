package com.douglasqueiroz.mywallet.android.feature.addtransaction.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.douglasqueiroz.mywallet.android.R
import com.douglasqueiroz.mywallet.android.compose.MaskVisualTransformation
import com.douglasqueiroz.mywallet.android.compose.MyWalletTextField
import com.douglasqueiroz.mywallet.android.feature.addtransaction.logic.AddTransactionEvent
import com.douglasqueiroz.mywallet.android.feature.addtransaction.logic.AddTransactionViewState
import com.douglasqueiroz.mywallet.android.util.Constants

@Composable
fun AddTransactionMain(
    state: AddTransactionViewState = AddTransactionViewState(),
    onEvent: (AddTransactionEvent) -> Unit = {  }
) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            MyWalletTextField(
                title = stringResource(id = R.string.add_transaction_date_label),
                text = state.date,
                onValueChange = { onEvent(AddTransactionEvent.OnDateChange(it)) },
                errorMsg = state.dateErrorMsg,
                visualTransformation = MaskVisualTransformation(Constants.DATE_MASK_YYYY_MM_DD)
            )

            MyWalletTextField(
                title = stringResource(id = R.string.add_transaction_quantity_label),
                text = state.quantity,
                onValueChange = { onEvent(AddTransactionEvent.OnQuantityChange(it)) },
                errorMsg = state.quantityErrorMsg
            )

            MyWalletTextField(
                title = stringResource(id = R.string.add_transaction_price_label),
                text = state.price,
                onValueChange = { onEvent(AddTransactionEvent.OnPriceChange(it)) },
                errorMsg = state.priceErrorMsg
            )

            MyWalletTextField(
                title = stringResource(id = R.string.add_transaction_total_label),
                text = state.total,
                onValueChange = { onEvent(AddTransactionEvent.OnTotalChange(it)) },
                errorMsg = state.totalErrorMsg,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    modifier = Modifier.weight(1.0f),
                    onClick = {
                        onEvent(AddTransactionEvent.OnBack)
                    }
                ) {
                    Text(stringResource(id = R.string.button_cancel))
                }

                Button(
                    modifier = Modifier.weight(1.0f),
                    onClick = {
                        onEvent(AddTransactionEvent.OnSave)
                    }
                ) {
                    Text(stringResource(id = R.string.button_add))
                }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun AddTransactionMainPreview() {
    AddTransactionMain()
}

@Preview(showBackground = true)
@Composable
fun AddTransactionMainPreviewWithError() {
    AddTransactionMain(
        state = AddTransactionViewState(quantityErrorMsg = "Invalid Quantity")
    )
}