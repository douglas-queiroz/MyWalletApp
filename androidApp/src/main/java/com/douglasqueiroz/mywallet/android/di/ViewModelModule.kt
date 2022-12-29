package com.douglasqueiroz.mywallet.android.di

import com.douglasqueiroz.mywallet.android.feature.main.logic.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {

    val module = module {
        viewModel {
            MainViewModel(
                loadOverallReportUseCase = get(),
                collectQuotationsUseCase = get()
            )
        }
    }
}