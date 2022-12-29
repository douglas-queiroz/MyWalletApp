package com.douglasqueiroz.mywallet.domain.usecases

import com.benasher44.uuid.uuid4
import com.douglasqueiroz.mywallet.data.model.CurrencyConversionEntity
import com.douglasqueiroz.mywallet.data.model.CurrencyEntity
import com.douglasqueiroz.mywallet.repository.local.CurrencyConversionDao
import com.douglasqueiroz.mywallet.repository.local.CurrencyDao
import kotlinx.datetime.Clock

interface AddBRLConversionEntityUseCase {
    suspend fun execute()
}

internal class AddBRLConversionEntityUseCaseImpl(
    private val currencyDao: CurrencyDao,
    private val currencyConversionDao: CurrencyConversionDao
): AddBRLConversionEntityUseCase {

    override suspend fun execute() {

        if (isBRLConversionNeededToBeInserted()) {
            return
        }

        val currencies = getCurrencies()

        val realCurrency = currencies.findLast { it.name == "Real" }
        val euroCurrency = currencies.findLast { it.name == "Euro" }
        val dollarCurrency = currencies.findLast { it.name == "Dolar" }

        addConversionEntity(
            from = realCurrency,
            to = euroCurrency,
            symbol = "BRLEUR=X"
        )

        addConversionEntity(
            from = realCurrency,
            to = dollarCurrency,
            symbol = "BRLUSD=X"
        )
    }

    private suspend fun addConversionEntity(
        from: CurrencyEntity?,
        to: CurrencyEntity?,
        symbol: String
    ) {
        val nowDateTime = Clock.System.now().toString()

        val entity = CurrencyConversionEntity(
            id = uuid4().toString(),
            currencyFromId = from?.id ?: throw IllegalArgumentException("Currency From ID is null"),
            currencyToId = to?.id ?: throw IllegalArgumentException("Currency To ID is null"),
            symbol = symbol,
            createdAt = nowDateTime,
            updatedAt = nowDateTime
        )

        currencyConversionDao.insert(entity)
    }

    private suspend fun getCurrencies() = currencyDao.getAll()

    private suspend fun isBRLConversionNeededToBeInserted() =
        currencyConversionDao.countBRLConversions() == 2L
}