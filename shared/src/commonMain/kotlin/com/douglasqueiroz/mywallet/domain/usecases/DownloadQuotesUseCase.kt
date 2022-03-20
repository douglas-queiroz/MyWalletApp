package com.douglasqueiroz.mywallet.domain.usecases

import com.benasher44.uuid.uuid4
import com.douglasqueiroz.mywallet.data.model.QuoteEntity
import com.douglasqueiroz.mywallet.repository.local.QuoteDao
import com.douglasqueiroz.mywallet.repository.remote.MyWalletOldApi

internal class DownloadQuotesUseCase(
    private val oldApi: MyWalletOldApi,
    private val quoteDao: QuoteDao
) {

    suspend fun execute(sharedIdMap: Map<Int, String>) {
        quoteDao.clean()

        oldApi.getQuotes().map {
            QuoteEntity(
                id = uuid4().toString(),
                date = it.date,
                price = it.price,
                shareId = sharedIdMap[it.shareId] ?: throw IllegalArgumentException("Share ID ${it.shareId} not found"),
                createdAt = it.createdAt,
                updatedAt = it.updateAt
            )
        }.forEach {
            quoteDao.insert(it)
        }
    }
}