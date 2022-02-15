package com.douglasqueiroz.mywallet.repository.local

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.data.model.Currency
import com.douglasqueiroz.mywallet.domain.dto.CurrencyDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext
//import kotlinx.datetime.Clock

internal class CurrencyDao(
    databaseDriverFactory: DatabaseDriverFactory
): BaseDao(databaseDriverFactory) {

    suspend fun clean() = withContext(Dispatchers.Default) {
        database.currencyQueries.deleteAll()
    }

    suspend fun insert(currencyDto: CurrencyDto) = withContext(Dispatchers.Default) {
        database.transaction {

            val instantNow = ""//Clock.System.now()

            database.currencyQueries.insert(
                id = currencyDto.id,
                name = currencyDto.name,
                symbol = currencyDto.symbol,
                created_at = currencyDto.createdAt.toString(),
                updated_at = currencyDto.updatedAt.toString()
            )
        }
    }

    suspend fun getAll(): List<Currency>  = withContext(Default) {
        return@withContext database.currencyQueries.getAll().executeAsList()
    }
}