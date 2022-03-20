package com.douglasqueiroz.mywallet.repository.local

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.data.model.QuoteEntity
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

internal class QuoteDao(
    databaseDriverFactory: DatabaseDriverFactory
): BaseDao(databaseDriverFactory) {

    suspend fun clean() = withContext(Default) {
        database.quoteQueries.deleteAll()
    }

    suspend fun insert(entity: QuoteEntity) = withContext(Default) {
        with(entity) {
            database.transaction {
                database.quoteQueries.insert(
                    id = id,
                    date = date,
                    price = price,
                    shareId = shareId,
                    createdAt = createdAt,
                    updatedAt = updatedAt
                )
            }
        }
    }
}