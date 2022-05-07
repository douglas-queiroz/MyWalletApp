package com.douglasqueiroz.mywallet.domain.usecases

import com.douglasqueiroz.mywallet.domain.dto.CurrencyDto
import com.douglasqueiroz.mywallet.repository.local.CurrencyDao

interface GetCurrenciesUseCase {

    suspend fun execute(): List<CurrencyDto>
}

internal class GetCurrenciesUseCaseImpl(
    private val currencyDao: CurrencyDao
): GetCurrenciesUseCase {

    override suspend fun execute(): List<CurrencyDto> {
        return currencyDao.getAll().map {
            CurrencyDto(
                id = it.id,
                name = it.name ?: "N/A",
                symbol = it.symbol ?: "N/A"
            )
        }
    }
}