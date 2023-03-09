package com.douglasqueiroz.mywallet.android.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.douglasqueiroz.mywallet.android.R

@Composable
fun MyWalletTopAppBar(
    actions: @Composable RowScope.() -> Unit = {},
    @StringRes title: Int = R.string.app_name
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = title),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        actions = actions
    )
}

@Preview
@Composable
fun PreviewMyWalletTopBar() {
    MyWalletTopAppBar()
}