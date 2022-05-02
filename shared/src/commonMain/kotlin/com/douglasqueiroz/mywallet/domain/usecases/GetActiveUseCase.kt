package com.douglasqueiroz.mywallet.domain.usecases

import com.douglasqueiroz.mywallet.domain.dto.ActiveDto
import com.douglasqueiroz.mywallet.domain.dto.TransactionDto
import com.douglasqueiroz.mywallet.repository.local.FixedIncomeDao
import com.douglasqueiroz.mywallet.repository.local.ShareDao
import com.douglasqueiroz.mywallet.repository.local.TransactionDao

interface GetActiveUseCase {

    suspend fun execute(id: String): ActiveDto?
}

internal class GetActiveUseCaseImpl(
    private val fixedIncomeDao: FixedIncomeDao,
    private val shareDao: ShareDao,
    private val transactionDao: TransactionDao
): GetActiveUseCase {

    override suspend fun execute(id: String): ActiveDto? {

        return getShare(id) ?: getFixedIncome(id)
    }

    private suspend fun getShare(id: String): ActiveDto? {
        return shareDao.getActive(id)?.let {
            ActiveDto(
                id = it.id,
                name = it.name ?: "",
                symbol = it.code ?: "",
                currency = it.symbol ?: "",
                total = calculateTotal(it.id, it.quantity, it.price),
                transactions = getTransactions(it.id)
            )
        }
    }

    private suspend fun getFixedIncome(id: String): ActiveDto? {
        return fixedIncomeDao.getActive(id = id)?.let {
            ActiveDto(
                id = it.id,
                name = it.name ?: "",
                symbol = "",
                currency = it.symbol ?: "",
                total = it.total ?: 0.0,
                transactions = getTransactions(it.id)
            )
        }
    }

    private suspend fun calculateTotal(id: String, quantity: Double?, price: Float?): Double {
        val qtd = quantity ?: return 0.0
        val prc = price ?: transactionDao.getLastByTransactionableId(id)?.total ?: return 0.0

        return qtd * prc
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