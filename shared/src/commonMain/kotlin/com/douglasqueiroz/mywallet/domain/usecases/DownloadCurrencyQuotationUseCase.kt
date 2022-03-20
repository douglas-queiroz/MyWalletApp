package com.douglasqueiroz.mywallet.domain.usecases

import com.benasher44.uuid.uuid4
import com.douglasqueiroz.mywallet.data.model.CurrencyQuotationEntity
import com.douglasqueiroz.mywallet.repository.local.CurrencyQuotationDao
import com.douglasqueiroz.mywallet.repository.remote.MyWalletOldApi

internal class DownloadCurrencyQuotationUseCase(
    private val oldApi: MyWalletOldApi,
    private val currencyQuotationDao: CurrencyQuotationDao
) {

    suspend fun execute(currencyConversionIdMap: Map<Int, String>) {
        currencyQuotationDao.clean()

        oldApi.getCurrencyQuotation().map {
            CurrencyQuotationEntity(
                id = uuid4().toString(),
                currencyConversionId = currencyConversionIdMap[it.currencyConversionId] ?: throw IllegalArgumentException("currencyConversionId ${it.currencyConversionId} not found"),
                date = it.date,
                price = it.price,
                createdAt = it.createdAt,
                updatedAt = it.updateAt
            )
        }.forEach {
            currencyQuotationDao.insert(it)
        }
    }
}