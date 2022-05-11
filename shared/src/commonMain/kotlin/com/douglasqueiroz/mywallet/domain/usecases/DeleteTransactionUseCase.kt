package com.douglasqueiroz.mywallet.domain.usecases

import com.douglasqueiroz.mywallet.repository.local.TransactionDao

interface DeleteTransactionUseCase {
    suspend fun execute(id: String)
}

internal class DeleteTransactionUseCaseImpl(
    private val transactionDao: TransactionDao
): DeleteTransactionUseCase {

    override suspend fun execute(id: String) {
        transactionDao.delete(id)
    }
}