package com.douglasqueiroz.mywallet.di

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.domain.usecases.*
import com.douglasqueiroz.mywallet.repository.local.*
import com.douglasqueiroz.mywallet.repository.remote.MyWalletOldApi
import com.douglasqueiroz.mywallet.repository.remote.QuotationAPI
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

        val downloadCurrencyConversionUseCase = DownloadCurrencyConversionUseCase(
            oldApi = oldApi,
            currencyConversionDao = CurrencyConversionDao(databaseDriverFactory)
        )

        val downloadQuotesUseCase = DownloadQuotesUseCase(
            oldApi = oldApi,
            quoteDao = QuoteDao(databaseDriverFactory)
        )

        val downloadCurrencyQuotationUseCase = DownloadCurrencyQuotationUseCase(
            oldApi = oldApi,
            currencyQuotationDao = CurrencyQuotationDao(databaseDriverFactory)
        )

        return LoadDatabaseUseCaseImpl(
            downloadCurrenciesUseCase = downloadCurrenciesUseCase,
            downloadFixedIncomesUseCase = downloadFixedIncomesUseCase,
            downloadSharesUseCase = downloadSharesUseCase,
            downloadTransactionsUseCase = downloadTransactionsUseCase,
            downloadCurrencyConversionUseCase = downloadCurrencyConversionUseCase,
            downloadQuotesUseCase = downloadQuotesUseCase,
            downloadCurrencyQuotationUseCase = downloadCurrencyQuotationUseCase
        )
    }

    fun getCalculateOverallReportUseCase(): CalculateOverallReportUseCase {
        return CalculateOverallReportUseCaseImpl(
            fixedIncomeDao = FixedIncomeDao(databaseDriverFactory),
            shareDao = ShareDao(databaseDriverFactory),
            currencyDao = CurrencyDao(databaseDriverFactory),
            transactionDao = TransactionDao(databaseDriverFactory)
        )
    }

    fun getCollectionQuotationsUseCase(): CollectQuotationsUseCase {
        return CollectQuotationsUseCaseImpl(
            shareDao = ShareDao(databaseDriverFactory),
            currencyConversionDao = CurrencyConversionDao(databaseDriverFactory),
            quotationAPI = QuotationAPI(),
            quoteDao = QuoteDao(databaseDriverFactory),
            currencyQuotationDao = CurrencyQuotationDao(databaseDriverFactory),
            dateUtil = DateUtil()
        )
    }

    fun getFetchShareByTypeUseCase(): FetchShareByTypeUseCase {
        return FetchShareByTypeUseCaseImpl(
            shareDao = ShareDao(databaseDriverFactory),
            transactionDao = TransactionDao((databaseDriverFactory))
        )
    }

    fun getFetchFixedIncomeByTypeUseCase(): FetchFixedIncomeByTypeUseCase {
        return FetchFixedIncomeByTypeUseCaseImpl(
            fixedIncomeDao = FixedIncomeDao(databaseDriverFactory),
            transactionDao = TransactionDao(databaseDriverFactory)
        )
    }

    fun getAddTransactionUseCase(): AddTransactionUseCase = AddTransactionUseCaseImpl(
        transactionDao = TransactionDao(databaseDriverFactory)
    )
}