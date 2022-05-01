package com.douglasqueiroz.mywallet.domain.usecases

import com.douglasqueiroz.mywallet.domain.dto.ActiveDto
import com.douglasqueiroz.mywallet.domain.dto.TransactionDto
import com.douglasqueiroz.mywallet.domain.enum.ShareType
import com.douglasqueiroz.mywallet.repository.local.ShareDao
import com.douglasqueiroz.mywallet.repository.local.TransactionDao

interface FetchShareByTypeUseCase {

    suspend fun execute(type: ShareType): List<ActiveDto>
}

internal class FetchShareByTypeUseCaseImpl(
    private val shareDao: ShareDao,
    private val transactionDao: TransactionDao
): FetchShareByTypeUseCase {

    override suspend fun execute(type: ShareType): List<ActiveDto> {
        return shareDao
            .getActive(type.ordinal.toLong())
            .map {
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

    private suspend fun getTransactions(transactionableId: String): List<TransactionDto> {
        return transactionDao.getByTransactionableId(transactionableId).map {
            TransactionDto(
                id = it.id,
                date = it.date ?: "",
                total = it.total?.toDouble() ?: 0.0
            )
        }
    }

    private suspend fun calculateTotal(id: String, quantity: Double?, price: Float?): Double {
        val qtd = quantity ?: return 0.0
        val prc = price ?: transactionDao.getLastByTransactionableId(id)?.total ?: return 0.0

        return qtd * prc
    }

}