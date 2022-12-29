package com.douglasqueiroz.mywallet.android.feature.main.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.douglasqueiroz.mywallet.android.feature.main.logic.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent(viewModel)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadReport()
    }
}
