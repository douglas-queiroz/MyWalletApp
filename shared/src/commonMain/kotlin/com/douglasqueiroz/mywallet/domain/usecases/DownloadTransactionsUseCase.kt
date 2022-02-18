package com.douglasqueiroz.mywallet.domain.usecases

import com.benasher44.uuid.uuid4
import com.douglasqueiroz.mywallet.data.model.FinancialTransactionEntity
import com.douglasqueiroz.mywallet.repository.local.TransactionDao
import com.douglasqueiroz.mywallet.repository.remote.MyWalletOldApi

internal class DownloadTransactionsUseCase(
    private val oldApi: MyWalletOldApi,
    private val transactionDao: TransactionDao
) {

    suspend fun execute(fixedIncomeIdMap: Map<Int, String>, shareIdMap: Map<Int, String>) {
        transactionDao.clean()
        oldApi.getTransactions().map {

            val transactionableId = getTransactionId(
                transactionableId = it.transactionableId,
                transactionableType = it.transactionableType,
                fixedIncomeIdMap = fixedIncomeIdMap,
                shareIdMap = shareIdMap
            ) ?: ""

            FinancialTransactionEntity(
                id = uuid4().toString(),
                date = it.date,
                quantity = it.quantity,
                price = it.price,
                total = it.total,
                transactionableId = transactionableId,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }.filter {
            it.transactionableId != ""
        }.forEach {
            transactionDao.insert(it)
        }
    }

    private fun getTransactionId(
        transactionableId: Int,
        transactionableType: String,
        fixedIncomeIdMap: Map<Int, String>,
        shareIdMap: Map<Int, String>
    ): String? {
        return when (transactionableType) {
            "Share" -> shareIdMap[transactionableId]
            "FixedIncome" -> fixedIncomeIdMap[transactionableId]
            else -> throw IllegalArgumentException("transactionableType $transactionableType not found")
        }
    }
}