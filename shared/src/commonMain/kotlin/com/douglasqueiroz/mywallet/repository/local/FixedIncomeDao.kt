package com.douglasqueiroz.mywallet.repository.local

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.data.model.FixedIncomeActive
import com.douglasqueiroz.mywallet.data.model.FixedIncomeActiveById
import com.douglasqueiroz.mywallet.data.model.FixedIncomeEntity
import com.douglasqueiroz.mywallet.data.model.FixedIncomeReportInfo
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
        return@withContext database.fixedIncomeQueries.fixedIncomeReportInfo(type.ordinal.toLong()).executeAsList()
    }

    suspend fun getActive(type: Long): List<FixedIncomeActive> = withContext(Default) {
        return@withContext database.fixedIncomeQueries.fixedIncomeActive(type).executeAsList()
    }

    suspend fun getActive(id: String): FixedIncomeActiveById? = withContext(Default) {
        return@withContext database.fixedIncomeQueries.fixedIncomeActiveById(id).executeAsOneOrNull()
    }
}