package com.douglasqueiroz.mywallet.domain.usecases

import com.douglasqueiroz.mywallet.domain.dto.ActiveDto
import com.douglasqueiroz.mywallet.domain.dto.TransactionDto
import com.douglasqueiroz.mywallet.domain.enum.FixedIncomeType
import com.douglasqueiroz.mywallet.repository.local.FixedIncomeDao
import com.douglasqueiroz.mywallet.repository.local.TransactionDao

interface FetchFixedIncomeByTypeUseCase {
    suspend fun execute(type: FixedIncomeType): List<ActiveDto>
}

internal class FetchFixedIncomeByTypeUseCaseImpl(
    private val fixedIncomeDao: FixedIncomeDao,
    private val transactionDao: TransactionDao
): FetchFixedIncomeByTypeUseCase {

    override suspend fun execute(type: FixedIncomeType) = fixedIncomeDao
        .getActive(type.ordinal.toLong())
        .map {
            ActiveDto(
                id = it.id,
                name = it.name ?: "",
                symbol = "",
                currency = it.symbol ?: "",
                total = it.total ?: 0.0,
                transactions = getTransactions(it.id)
            )
        }

    private suspend fun getTransactions(transactionableId: String): List<TransactionDto> {
        return transactionDao.getByTransactionableId(transactionableId).map {
            TransactionDto(
                id = it.id,
                date = it.date ?: "",
                total = it.total?.toDouble() ?: 0.0
            )
        }
    }
}