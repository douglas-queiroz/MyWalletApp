package com.douglasqueiroz.mywallet.android.compose

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable

@Composable
fun MyWalletLoading(show: Boolean) {
    if (show) {
        CircularProgressIndicator()
    }
}