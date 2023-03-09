package com.douglasqueiroz.mywallet.android.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MyWalletTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    title: String,
    text: String = "",
    onValueChange: (String) -> Unit = {},
    errorMsg: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    val isError = errorMsg != null

    Column(modifier =  modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(title) },
            value = text,
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            isError = isError,
            visualTransformation = visualTransformation
        )

        errorMsg?.let {
            Text(
                modifier = Modifier.fillMaxWidth().padding(start = 4.dp),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.error,
                text = it
            )
        }
    }

}

@Composable
@Preview(showBackground = true)
fun MyWalletTextField_Preview() {
    MyWalletTextField(
        title = "Price"
    )
}

@Composable
@Preview(showBackground = true)
fun MyWalletTextField_Preview_with_error() {
    MyWalletTextField(
        title = "Price",
        text = "12.50",
        errorMsg = "Invalid Price"
    )
}