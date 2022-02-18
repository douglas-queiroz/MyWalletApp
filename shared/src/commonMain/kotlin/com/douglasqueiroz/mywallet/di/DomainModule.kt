package com.douglasqueiroz.mywallet.di

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.domain.usecases.*
import com.douglasqueiroz.mywallet.domain.usecases.DownloadCurrenciesUseCase
import com.douglasqueiroz.mywallet.domain.usecases.DownloadFixedIncomesUseCase
import com.douglasqueiroz.mywallet.domain.usecases.DownloadTransactionsUseCase
import com.douglasqueiroz.mywallet.domain.usecases.LoadDatabaseUseCaseImpl
import com.douglasqueiroz.mywallet.repository.local.CurrencyDao
import com.douglasqueiroz.mywallet.repository.local.FixedIncomeDao
import com.douglasqueiroz.mywallet.repository.local.ShareDao
import com.douglasqueiroz.mywallet.repository.local.TransactionDao
import com.douglasqueiroz.mywallet.repository.remote.MyWalletOldApi
import com.douglasqueiroz.mywallet.util.DateUtil

class DomainModule(
    private val databaseDriverFactory: DatabaseDriverFactory
) {

    fun getLoadDatabaseUseCase() : LoadDatabaseUseCase {
        val oldApi = MyWalletOldApi()

        val downloadCurrenciesUseCase = DownloadCurrenciesUseCase(
            oldApi = oldApi,
            currencyDao = CurrencyDao((databaseDriverFactory))
        )

        val downloadFixedIncomesUseCase = DownloadFixedIncomesUseCase(
            oldApi = oldApi,
            fixedIncomeDao = FixedIncomeDao(databaseDriverFactory)
        )

        val downloadSharesUseCase = DownloadSharesUseCase(
            oldApi = oldApi,
            shareDao = ShareDao(databaseDriverFactory)
        )

        val downloadTransactionsUseCase = DownloadTransactionsUseCase(
            oldApi = oldApi,
            transactionDao = TransactionDao(databaseDriverFactory)
        )


        return LoadDatabaseUseCaseImpl(
            downloadCurrenciesUseCase = downloadCurrenciesUseCase,
            downloadFixedIncomesUseCase = downloadFixedIncomesUseCase,
            downloadSharesUseCase = downloadSharesUseCase,
            downloadTransactionsUseCase = downloadTransactionsUseCase
        )
    }
}