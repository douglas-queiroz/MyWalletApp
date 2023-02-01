package com.douglasqueiroz.mywallet.android.feature.main.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.douglasqueiroz.mywallet.android.compose.SetupNavHost

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetupNavHost()
        }
    }
}
