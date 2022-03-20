package com.douglasqueiroz.mywallet.domain.usecases

interface LoadDatabaseUseCase {
    suspend fun execute()
}

internal class LoadDatabaseUseCaseImpl(
    private val downloadCurrenciesUseCase: DownloadCurrenciesUseCase,
    private val downloadFixedIncomesUseCase: DownloadFixedIncomesUseCase,
    private val downloadSharesUseCase: DownloadSharesUseCase,
    private val downloadTransactionsUseCase: DownloadTransactionsUseCase,
    private val downloadCurrencyConversionUseCase: DownloadCurrencyConversionUseCase,
    private val downloadQuotesUseCase: DownloadQuotesUseCase,
    private val downloadCurrencyQuotationUseCase: DownloadCurrencyQuotationUseCase
) : LoadDatabaseUseCase {

    override suspend fun execute() {
        val currencyIdMap = downloadCurrenciesUseCase.execute()
        val fixedIncomeIdMap = downloadFixedIncomesUseCase.execute(currencyIdMap)
        val shareIdMap = downloadSharesUseCase.execute(currencyIdMap)
        val currencyConversionIdMap = downloadCurrencyConversionUseCase.execute(currencyIdMap)
        downloadTransactionsUseCase.execute(
            fixedIncomeIdMap = fixedIncomeIdMap,
            shareIdMap = shareIdMap
        )
        downloadQuotesUseCase.execute(shareIdMap)
        downloadCurrencyQuotationUseCase.execute(currencyConversionIdMap)
    }
}