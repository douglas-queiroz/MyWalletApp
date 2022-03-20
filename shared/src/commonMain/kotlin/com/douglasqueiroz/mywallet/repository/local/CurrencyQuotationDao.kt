package com.douglasqueiroz.mywallet.repository.local

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.data.model.CurrencyQuotationEntity
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

internal class CurrencyQuotationDao(
    databaseDriverFactory: DatabaseDriverFactory
): BaseDao(databaseDriverFactory) {

    suspend fun clean() = withContext(Default) { database.currencyQuotationQueries.deleteAll() }

    suspend fun insert(entity: CurrencyQuotationEntity) = withContext(Default) {
        database.transaction {
            database.currencyQuotationQueries.insert(
                id = entity.id,
                currencyConversionId = entity.currencyConversionId,
                date = entity.date,
                price = entity.price,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt
            )
        }
    }
}