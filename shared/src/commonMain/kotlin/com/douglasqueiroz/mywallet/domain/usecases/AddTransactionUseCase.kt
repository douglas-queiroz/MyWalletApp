package com.douglasqueiroz.mywallet.domain.usecases

import com.benasher44.uuid.uuid4
import com.douglasqueiroz.mywallet.data.model.FinancialTransactionEntity
import com.douglasqueiroz.mywallet.domain.dto.AssetDto
import com.douglasqueiroz.mywallet.extensions.toOnlyDate
import com.douglasqueiroz.mywallet.repository.local.TransactionDao
import kotlinx.datetime.*

interface AddTransactionUseCase {

    suspend fun execute(
        asset: AssetDto,
        date: String? = null,
        quantity: Float,
        price: Float
    )
}

internal class AddTransactionUseCaseImpl(
    private val transactionDao: TransactionDao
): AddTransactionUseCase {

    override suspend fun execute(
        asset: AssetDto,
        date: String?,
        quantity: Float,
        price: Float
    ) {

        val qtd = quantity ?: 1.0f
        val now = Clock.System.now()


        FinancialTransactionEntity(
            id = uuid4().toString(),
            date = date ?: now.toOnlyDate(),
            quantity = quantity,
            price = price,
            total = price * qtd,
            transactionableId = asset.id,
            createdAt = now.toString(),
            updatedAt = now.toString()
        ).also {
            transactionDao.insert(it)
        }
    }
}
