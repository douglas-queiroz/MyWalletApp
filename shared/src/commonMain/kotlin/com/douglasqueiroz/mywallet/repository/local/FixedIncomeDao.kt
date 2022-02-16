package com.douglasqueiroz.mywallet.repository.local

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.data.model.FixedIncomeEntity
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

internal class FixedIncomeDao(databaseDriverFactory: DatabaseDriverFactory): BaseDao(databaseDriverFactory) {

    suspend fun clean() = withContext(Default) { database.fixedIncomeQueries.deleteAll() }

    suspend fun insert(entity: FixedIncomeEntity) = withContext(Default) {
        database.transaction {
            database.fixedIncomeQueries.insert(
                id = entity.id,
                name = entity.name,
                currencyId = entity.currencyId,
                type = entity.type,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt
            )
        }
    }
}