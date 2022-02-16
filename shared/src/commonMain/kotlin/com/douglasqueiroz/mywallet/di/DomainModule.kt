package com.douglasqueiroz.mywallet.di

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.domain.usecases.LoadDatabaseUseCase
import com.douglasqueiroz.mywallet.domain.usecases.LoadDatabaseUseCaseImpl
import com.douglasqueiroz.mywallet.repository.local.CurrencyDao
import com.douglasqueiroz.mywallet.repository.local.FixedIncomeDao
import com.douglasqueiroz.mywallet.repository.local.ShareDao
import com.douglasqueiroz.mywallet.repository.remote.MyWalletOldApi
import com.douglasqueiroz.mywallet.util.DateUtil

class DomainModule(
    private val databaseDriverFactory: DatabaseDriverFactory
) {

    fun getLoadDatabaseUseCase() : LoadDatabaseUseCase {
        return LoadDatabaseUseCaseImpl(
            oldApi = MyWalletOldApi(),
            currencyDao = CurrencyDao(databaseDriverFactory),
            fixedIncomeDao = FixedIncomeDao(databaseDriverFactory),
            shareDao = ShareDao(databaseDriverFactory)
        )
    }
}