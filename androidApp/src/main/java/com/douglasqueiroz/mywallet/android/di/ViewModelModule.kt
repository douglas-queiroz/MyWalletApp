package com.douglasqueiroz.mywallet.android.di

import com.douglasqueiroz.mywallet.android.feature.assetlist.logic.FixedIncomeAssetListViewModel
import com.douglasqueiroz.mywallet.android.feature.assetlist.logic.ShareAssetListViewModel
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

        viewModel { parametersHolder ->
            FixedIncomeAssetListViewModel(
                fixedIncomeType = parametersHolder.get(),
                fetchFixedIncomeByTypeUseCase =  get()
            )
        }

        viewModel { parametersHolder ->
            ShareAssetListViewModel(
                shareType = parametersHolder.get(),
                fetchShareByTypeUseCase = get()
            )
        }
    }
}