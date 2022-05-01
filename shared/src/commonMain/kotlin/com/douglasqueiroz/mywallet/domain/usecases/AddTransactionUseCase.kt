package com.douglasqueiroz.mywallet.domain.usecases

import com.benasher44.uuid.uuid4
import com.douglasqueiroz.mywallet.data.model.FinancialTransactionEntity
import com.douglasqueiroz.mywallet.domain.dto.ActiveDto
import com.douglasqueiroz.mywallet.extensions.toOnlyDate
import com.douglasqueiroz.mywallet.repository.local.TransactionDao
import kotlinx.datetime.*

interface AddTransactionUseCase {

    suspend fun execute(
        active: ActiveDto,
        date: Instant = Clock.System.now(),
        quantity: Float? = null,
        price: Float
    )
}

internal class AddTransactionUseCaseImpl(
    private val transactionDao: TransactionDao
): AddTransactionUseCase {

    override suspend fun execute(
        active: ActiveDto,
        date: Instant,
        quantity: Float?,
        price: Float
    ) {

        val qtd = quantity ?: 1.0f
        val now = Clock.System.now().toString()

        FinancialTransactionEntity(
            id = uuid4().toString(),
            date = date.toOnlyDate(),
            quantity = quantity,
            price = price,
            total = price * qtd,
            transactionableId = active.id,
            createdAt = now,
            updatedAt = now
        ).also {
            transactionDao.insert(it)
        }
    }
}
