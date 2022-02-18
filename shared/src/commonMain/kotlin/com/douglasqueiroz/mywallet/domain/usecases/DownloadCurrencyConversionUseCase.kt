package com.douglasqueiroz.mywallet.domain.usecases

import com.benasher44.uuid.uuid4
import com.douglasqueiroz.mywallet.data.model.CurrencyConversionEntity
import com.douglasqueiroz.mywallet.repository.local.CurrencyConversionDao
import com.douglasqueiroz.mywallet.repository.remote.MyWalletOldApi

internal class DownloadCurrencyConversionUseCase(
    private val oldApi: MyWalletOldApi,
    private val currencyConversionDao: CurrencyConversionDao
) {

    suspend fun execute(currencyIdMap: Map<Int, String>) {
        currencyConversionDao.clean()

        oldApi.getCurrencyConversion().map {
            CurrencyConversionEntity(
                id = uuid4().toString(),
                currencyFromId = currencyIdMap[it.currencyFromId] ?: throw IllegalArgumentException("Currency ID ${it.currencyFromId} not found"),
                currencyToId = currencyIdMap[it.currencyToId] ?: throw IllegalArgumentException("Currency ID ${it.currencyToId} not found"),
                symbol = it.symbol,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }.forEach {
            currencyConversionDao.insert(it)
        }
    }
}