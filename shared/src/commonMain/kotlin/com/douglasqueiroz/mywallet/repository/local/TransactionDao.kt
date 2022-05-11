package com.douglasqueiroz.mywallet.repository.local

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.data.model.FinancialTransactionEntity
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

internal class TransactionDao(databaseDriverFactory: DatabaseDriverFactory): BaseDao(databaseDriverFactory) {

    suspend fun clean() = withContext(Default) {
        database.financialTransactionQueries.deleteAll()
    }

    suspend fun insert(transaction: FinancialTransactionEntity) = withContext(Default) {
        database.transaction {
            database.financialTransactionQueries.insert(
                id = transaction.id,
                date = transaction.date,
                quantity = transaction.quantity,
                price = transaction.price,
                total = transaction.total,
                transactionableId = transaction.transactionableId,
                createdAt = transaction.createdAt,
                updatedAt = transaction.updatedAt
            )
        }
    }

    suspend fun getLastByTransactionableId(transactionableId: String): FinancialTransactionEntity? = withContext(Default) {
        return@withContext database
            .financialTransactionQueries
            .getLastTransactionByTransactionableId(transactionableId)
            .executeAsOneOrNull()
    }

    suspend fun getByTransactionableId(transactionableId: String) = withContext(Default) {
        return@withContext database
            .financialTransactionQueries
            .getTransactionByTransactionableId(transactionableId)
            .executeAsList()
    }

    suspend fun delete(id: String) = withContext(Default) {
        database.financialTransactionQueries.deleteById(id)
    }
}