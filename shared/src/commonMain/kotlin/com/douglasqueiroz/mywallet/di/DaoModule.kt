package com.douglasqueiroz.mywallet.di

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.repository.local.*
import org.koin.dsl.module

class DaoModule(
    private val databaseDriverFactory: DatabaseDriverFactory
) {

    val module = module {
        factory { CurrencyDao(databaseDriverFactory) }
        factory { CurrencyConversionDao(databaseDriverFactory) }
        factory { CurrencyQuotationDao(databaseDriverFactory) }
        factory { FixedIncomeDao(databaseDriverFactory) }
        factory { QuoteDao(databaseDriverFactory) }
        factory { ShareDao(databaseDriverFactory) }
        factory { TransactionDao(databaseDriverFactory) }
    }
}