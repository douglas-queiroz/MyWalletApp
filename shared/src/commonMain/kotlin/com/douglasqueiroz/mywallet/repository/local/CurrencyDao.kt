package com.douglasqueiroz.mywallet.repository.local

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.data.model.CurrencyEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

internal class CurrencyDao(
    databaseDriverFactory: DatabaseDriverFactory
): BaseDao(databaseDriverFactory) {

    suspend fun clean() = withContext(Default) {
        database.currencyQueries.deleteAll()
    }

    suspend fun insert(currencyEntity: CurrencyEntity) = withContext(Dispatchers.Default) {
        database.transaction {
            database.currencyQueries.insert(
                id = currencyEntity.id,
                name = currencyEntity.name,
                symbol = currencyEntity.symbol,
                createdAt = currencyEntity.createdAt,
                updatedAt = currencyEntity.updatedAt
            )
        }
    }

    suspend fun getAll(): List<CurrencyEntity>  = withContext(Default) {
        return@withContext database.currencyQueries.getAll().executeAsList()
    }
}