package com.douglasqueiroz.mywallet.domain.usecases

import com.douglasqueiroz.mywallet.domain.dto.CurrencyDto
import com.douglasqueiroz.mywallet.repository.local.CurrencyDao
import com.douglasqueiroz.mywallet.repository.remote.CurrencyApi

interface LoadDatabaseUseCase {
    suspend fun execute(): String
}

internal class LoadDatabaseUseCaseImpl(
    private val currencyDao: CurrencyDao,
    private val currencyApi: CurrencyApi
): LoadDatabaseUseCase {

    override suspend fun execute(): String {
        currencyDao.clean()

        currencyApi.getCurrencies()
            .map {
                CurrencyDto(it.id.toString(), it.name, it.symbol)
            }
            .forEach {
            currencyDao.insert(it)
        }

        return currencyDao.getAll().map {
            it.name
        }.joinToString(",")
    }
}