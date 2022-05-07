package com.douglasqueiroz.mywallet.repository.local

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.data.model.*
import com.douglasqueiroz.mywallet.domain.enum.ShareType
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

internal class ShareDao(databaseDriverFactory: DatabaseDriverFactory): BaseDao(databaseDriverFactory) {

    suspend fun clean() = withContext(Default) { database.shareQueries.deleteAll() }

    suspend fun insert(entity: ShareEntity) = withContext(Default) {
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

    suspend fun getReportInfoByType(type: ShareType): List<ShareReportInfo> = withContext(Default) {
        return@withContext database.shareQueries.shareReportInfo(type.ordinal.toLong()).executeAsList()
    }

    suspend fun getAll(): List<ShareEntity> = withContext(Default) {
        return@withContext database.shareQueries.getAll().executeAsList()
    }

    suspend fun getAsset(type: Long): List<GetShareAssetByType> = withContext(Default) {
        return@withContext database.shareQueries.getShareAssetByType(type).executeAsList()
    }

    suspend fun getAsset(id: String): GetShareAssetById? = withContext(Default) {
        return@withContext database.shareQueries.getShareAssetById(id).executeAsOneOrNull()
    }
}