package com.douglasqueiroz.mywallet.repository.local

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.data.model.*
import com.douglasqueiroz.mywallet.domain.enum.FixedIncomeType
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

    suspend fun getReportInfoByType(type: FixedIncomeType): List<FixedIncomeReportInfo> = withContext(Default) {
        return@withContext database
            .fixedIncomeQueries
            .fixedIncomeReportInfo(type.ordinal.toLong())
            .executeAsList()
    }

    suspend fun getAsset(type: Long): List<GetFixedIncomeAssetByType> = withContext(Default) {
        return@withContext database
            .fixedIncomeQueries
            .getFixedIncomeAssetByType(type)
            .executeAsList()
    }

    suspend fun getAsset(id: String): GetFixedIncomeAssetById? = withContext(Default) {
        return@withContext database
            .fixedIncomeQueries
            .getFixedIncomeAssetById(id)
            .executeAsOneOrNull()
    }
}