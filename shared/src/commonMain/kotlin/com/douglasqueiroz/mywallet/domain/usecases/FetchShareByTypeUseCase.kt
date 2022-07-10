package com.douglasqueiroz.mywallet.domain.usecases

import com.douglasqueiroz.mywallet.domain.dto.AssetDto
import com.douglasqueiroz.mywallet.domain.dto.CurrencyDto
import com.douglasqueiroz.mywallet.domain.dto.TransactionDto
import com.douglasqueiroz.mywallet.domain.enum.ShareType
import com.douglasqueiroz.mywallet.repository.local.ShareDao
import com.douglasqueiroz.mywallet.repository.local.TransactionDao

interface FetchShareByTypeUseCase {

    suspend fun execute(type: ShareType): List<AssetDto>
}

internal class FetchShareByTypeUseCaseImpl(
    private val shareDao: ShareDao,
    private val transactionDao: TransactionDao
): FetchShareByTypeUseCase {

    override suspend fun execute(type: ShareType): List<AssetDto> {
        return shareDao
            .getAsset(type.ordinal.toLong())
            .map {
                AssetDto(
                    id = it.id,
                    name = it.name ?: "",
                    code = it.code ?: "",
                    amount = it.quantity ?: 0.0,
                    currency = CurrencyDto(it.currencyId, it.currencyName ?: "", it.currencySymbol ?: ""),
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
                quantity = it.quantity?.toDouble() ?: 00.0,
                price = it.price?.toDouble() ?: 0.0,
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