package com.douglasqueiroz.mywallet.android.feature.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.douglasqueiroz.mywallet.android.R

@Composable
fun MainHeader(
    total: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(34.dp).fillMaxWidth()
    ) {

        Text(
            text = stringResource(R.string.total),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = total,
            fontSize = 22.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainHeader() {
    MaterialTheme {
        MainHeader(total = "R$ 100")
    }
}