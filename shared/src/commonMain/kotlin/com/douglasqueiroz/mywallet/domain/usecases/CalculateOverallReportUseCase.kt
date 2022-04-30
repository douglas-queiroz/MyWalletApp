package com.douglasqueiroz.mywallet.domain.usecases

import com.douglasqueiroz.mywallet.data.model.FixedIncomeReportInfo
import com.douglasqueiroz.mywallet.data.model.ShareReportInfo
import com.douglasqueiroz.mywallet.domain.dto.OverallReportDto
import com.douglasqueiroz.mywallet.domain.enum.FixedIncomeType
import com.douglasqueiroz.mywallet.domain.enum.ShareType
import com.douglasqueiroz.mywallet.repository.local.CurrencyDao
import com.douglasqueiroz.mywallet.repository.local.FixedIncomeDao
import com.douglasqueiroz.mywallet.repository.local.ShareDao
import com.douglasqueiroz.mywallet.repository.local.TransactionDao

interface CalculateOverallReportUseCase {
    suspend fun execute(): List<OverallReportDto>
}

internal class CalculateOverallReportUseCaseImpl(
    private val fixedIncomeDao: FixedIncomeDao,
    private val shareDao: ShareDao,
    private val currencyDao: CurrencyDao,
    private val transactionDao: TransactionDao
): CalculateOverallReportUseCase {

    override suspend fun execute(): List<OverallReportDto> {

        val savingTotal = fixedIncomeDao
            .getReportInfoByType(FixedIncomeType.SAVING_ACCOUNT)
            .sumOf { convertToEuroA(it) }

        val stockTotal = shareDao
            .getReportInfoByType(ShareType.Stock)
            .sumOf { convertToEuro(it) }

        val reitTotal = shareDao
            .getReportInfoByType(ShareType.REIT)
            .sumOf { convertToEuro(it) }

        val goldTotal = shareDao
            .getReportInfoByType(ShareType.GOLD)
            .sumOf {
                convertToEuro(it)
            }

        val bitcoinTotal = shareDao
            .getReportInfoByType(ShareType.BITCOIN)
            .sumOf {
                convertToEuro(it)
            }

        val total = savingTotal + stockTotal + reitTotal + bitcoinTotal + goldTotal

        return listOf(
            createOverallReportDto(
                name = "Saving Account",
                total = savingTotal,
                totalOverall = total,
                fixedIncomeType = FixedIncomeType.SAVING_ACCOUNT
            ),
            createOverallReportDto(
                name ="Stock",
                total = stockTotal,
                totalOverall = total,
                shareType = ShareType.Stock
            ),
            createOverallReportDto(
                name = "Reit",
                total = reitTotal,
                totalOverall = total,
                shareType = ShareType.REIT
            ),
            createOverallReportDto(
                name = "Bitcoin",
                total = bitcoinTotal,
                totalOverall = total,
                shareType = ShareType.BITCOIN
            ),
            createOverallReportDto(
                name = "Gold",
                total = goldTotal,
                totalOverall = total,
                shareType = ShareType.GOLD
            )
        )
    }

    private fun createOverallReportDto(
        name: String,
        total: Double,
        totalOverall: Double,
        shareType: ShareType? = null,
        fixedIncomeType: FixedIncomeType? = null
    ): OverallReportDto {
        return OverallReportDto(
            name = name,
            total = total,
            percentage = total * 100 / totalOverall,
            shareType = shareType,
            fixedIncomeType = fixedIncomeType
        )
    }

    private suspend fun convertToEuroA(reportInfo: FixedIncomeReportInfo): Double {
        val currencyIdTo  = reportInfo.currencyId
        val rate = currencyDao.getCurrencyRate(currencyIdTo)
        val price = reportInfo.price ?: 0.0

        return price / rate
    }

    private suspend fun convertToEuro(reportInfo: ShareReportInfo): Double {
        val currencyIdTo  = reportInfo.currencyId
        val rate = currencyDao.getCurrencyRate(currencyIdTo)
        val price = reportInfo.price?.toDouble() ?: transactionDao.getByTransactionableId(reportInfo.id)?.price?.toDouble() ?: 0.0
        val quantity = reportInfo.quantity ?: 0.0

        return (price / rate * quantity)
    }
}