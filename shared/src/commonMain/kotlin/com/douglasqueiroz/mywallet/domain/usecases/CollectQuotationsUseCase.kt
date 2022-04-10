package com.douglasqueiroz.mywallet.domain.usecases

import com.benasher44.uuid.uuid4
import com.douglasqueiroz.mywallet.data.model.CurrencyQuotationEntity
import com.douglasqueiroz.mywallet.data.model.QuoteEntity
import com.douglasqueiroz.mywallet.repository.local.CurrencyConversionDao
import com.douglasqueiroz.mywallet.repository.local.CurrencyQuotationDao
import com.douglasqueiroz.mywallet.repository.local.QuoteDao
import com.douglasqueiroz.mywallet.repository.local.ShareDao
import com.douglasqueiroz.mywallet.repository.remote.QuotationAPI
import com.douglasqueiroz.mywallet.util.DateUtil

interface CollectQuotationsUseCase {
    suspend fun execute()
}

internal class CollectQuotationsUseCaseImpl(
    private val shareDao: ShareDao,
    private val currencyConversionDao: CurrencyConversionDao,
    private val quotationAPI: QuotationAPI,
    private val currencyQuotationDao: CurrencyQuotationDao,
    private val quoteDao: QuoteDao,
    private val dateUtil: DateUtil
): CollectQuotationsUseCase {

    override suspend fun execute() {

        var currencyConversionMap = mutableMapOf<String, String>()
        val codes = currencyConversionDao.getAll()
            .filter {
                it.symbol != null
            }.map {
                currencyConversionMap[it.symbol!!] = it.id
                it.symbol
            }.toMutableList()


        val shareMap = mutableMapOf<String, String>()
        codes += shareDao.getAll()
            .filter {
                it.code != null || it.code != "N/A"
            }.map {
                shareMap[it.code!!] = it.id
                it.code!!
            }

        val quotationsResponses = quotationAPI.getQuotations(codes = codes)

        quotationsResponses.quoteResponse.quotationResultResponse.filter {
            it.symbol.contains("=X")
        }.map {
            CurrencyQuotationEntity(
                id = uuid4().toString(),
                currencyConversionId = currencyConversionMap[it.symbol]!!,
                date = dateUtil.timeStampToString(it.date),
                price = it.price.toFloat(),
                createdAt = dateUtil.getDateTimeNow().toString(),
                updatedAt = dateUtil.getDateTimeNow().toString()
            )
        }.forEach {
            currencyQuotationDao.insert(it)
        }

        quotationsResponses.quoteResponse.quotationResultResponse.filter {
            !it.symbol.contains("=X")
        }.map {
            QuoteEntity(
                id = uuid4().toString(),
                shareId = shareMap[it.symbol]!!,
                date = dateUtil.timeStampToString(it.date),
                price = it.price.toFloat(),
                createdAt = dateUtil.getDateTimeNow().toString(),
                updatedAt = dateUtil.getDateTimeNow().toString()
            )
        }.forEach {
            quoteDao.insert(it)
        }

    }
}