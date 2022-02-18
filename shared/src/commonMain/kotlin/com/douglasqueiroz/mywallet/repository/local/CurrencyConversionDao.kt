package com.douglasqueiroz.mywallet.repository.local

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.data.model.CurrencyConversionEntity
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

internal class CurrencyConversionDao(
    databaseDriverFactory: DatabaseDriverFactory
): BaseDao(databaseDriverFactory) {

    suspend fun clean() = withContext(Default) {
        database.currencyConversionQueries.deleteAll()
    }

    suspend fun insert(currencyConversionEntity: CurrencyConversionEntity) = withContext(Default) {
        database.currencyConversionQueries.transaction {
            with(currencyConversionEntity) {
                database.currencyConversionQueries.insert(
                    id = id,
                    currencyFromId = currencyFromId,
                    currencyToId = currencyToId,
                    symbol = symbol,
                    createdAt = createdAt,
                    updatedAt = updatedAt
                )
            }
        }
    }
}