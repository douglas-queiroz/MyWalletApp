package com.douglasqueiroz.mywallet.repository.local

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.data.model.ShareEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ShareDao(databaseDriverFactory: DatabaseDriverFactory): BaseDao(databaseDriverFactory) {

    suspend fun clean() = withContext(Dispatchers.Default) { database.shareQueries.deleteAll() }

    suspend fun insert(entity: ShareEntity) = withContext(Dispatchers.Default) {
        database.transaction {
            database.shareQueries.insert(
                id = entity.id,
                name = entity.name,
                code = entity.code,
                currencyId = entity.currencyId,
                type = entity.type,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt
            )
        }
    }
}