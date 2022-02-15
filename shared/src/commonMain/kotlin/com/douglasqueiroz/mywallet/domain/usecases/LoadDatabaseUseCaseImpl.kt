package com.douglasqueiroz.mywallet.domain.usecases

import com.benasher44.uuid.uuid4
import com.douglasqueiroz.mywallet.domain.dto.CurrencyDto
import com.douglasqueiroz.mywallet.repository.local.CurrencyDao
import com.douglasqueiroz.mywallet.repository.remote.CurrencyApi
import com.douglasqueiroz.mywallet.util.DateUtil

interface LoadDatabaseUseCase {
    suspend fun execute(): String
}

internal class LoadDatabaseUseCaseImpl(
    private val currencyDao: CurrencyDao,
    private val currencyApi: CurrencyApi,
    private val dateUtil: DateUtil
): LoadDatabaseUseCase {

    override suspend fun execute(): String {
        downloadCurrencies()

        return currencyDao.getAll().map {
            it.name
        }.joinToString(",")
    }

    private suspend fun downloadCurrencies() {
        currencyDao.clean()
        currencyApi.getCurrencies().map {
            val dateNow = dateUtil.getDateTimeNow()

            CurrencyDto(
                id = uuid4().toString(),
                name = it.name,
                symbol = it.symbol,
                createdAt = dateNow,
                updatedAt = dateNow
            )
        }.forEach {
            currencyDao.insert(it)
        }
    }


}