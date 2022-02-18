package com.douglasqueiroz.mywallet.domain.usecases

import com.benasher44.uuid.uuid4
import com.douglasqueiroz.mywallet.data.model.CurrencyEntity
import com.douglasqueiroz.mywallet.repository.local.CurrencyDao
import com.douglasqueiroz.mywallet.repository.remote.MyWalletOldApi

internal class DownloadCurrenciesUseCase(
    private val oldApi: MyWalletOldApi,
    private val currencyDao: CurrencyDao,
) {

    suspend fun execute(): Map<Int, String> {
        val currencyIdMap = mutableMapOf<Int, String>()
        currencyDao.clean()
        oldApi.getCurrencies().map {

            val uuid = uuid4().toString()

            currencyIdMap[it.id] = uuid

            CurrencyEntity(
                id = uuid,
                name = it.name,
                symbol = it.symbol,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }.forEach {
            currencyDao.insert(it)
        }

        return currencyIdMap
    }
}