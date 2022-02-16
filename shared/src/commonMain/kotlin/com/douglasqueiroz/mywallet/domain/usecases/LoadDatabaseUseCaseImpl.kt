package com.douglasqueiroz.mywallet.domain.usecases

import com.benasher44.uuid.uuid4
import com.douglasqueiroz.mywallet.data.model.CurrencyEntity
import com.douglasqueiroz.mywallet.data.model.FixedIncomeEntity
import com.douglasqueiroz.mywallet.data.model.ShareEntity
import com.douglasqueiroz.mywallet.domain.enum.FixedIncomeType
import com.douglasqueiroz.mywallet.domain.enum.ShareType
import com.douglasqueiroz.mywallet.repository.local.CurrencyDao
import com.douglasqueiroz.mywallet.repository.local.FixedIncomeDao
import com.douglasqueiroz.mywallet.repository.local.ShareDao
import com.douglasqueiroz.mywallet.repository.remote.MyWalletOldApi
import com.douglasqueiroz.mywallet.util.DateUtil

interface LoadDatabaseUseCase {
    suspend fun execute()
}

internal class LoadDatabaseUseCaseImpl(
    private val oldApi: MyWalletOldApi,
    private val currencyDao: CurrencyDao,
    private val fixedIncomeDao: FixedIncomeDao,
    private val shareDao: ShareDao
) : LoadDatabaseUseCase {

    override suspend fun execute() {

        val currencyIdMap = downloadCurrencies()
        downloadFixedIncomes(currencyIdMap = currencyIdMap)
        downloadShares(currencyIdMap = currencyIdMap)
    }

    private suspend fun downloadCurrencies(): Map<Int, String> {
        val currencyIdMap = mutableMapOf<Int, String>()
        currencyDao.clean()
        oldApi.getCurrencies().map {

            val uuid = uuid4().toString()

            currencyIdMap[it.id] = uuid

            CurrencyEntity(
                id = uuid,
                name = it.name,
                symbol = it.symbol,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }.forEach {
            currencyDao.insert(it)
        }

        return currencyIdMap
    }

    private suspend fun downloadFixedIncomes(currencyIdMap: Map<Int, String>) {
        fixedIncomeDao.clean()

        oldApi.getFixedIncomes().map {
            FixedIncomeEntity(
                id = uuid4().toString(),
                name = it.name,
                currencyId = currencyIdMap[it.currencyId]!!,
                type = FixedIncomeType.SAVING_ACCOUNT.ordinal.toLong(),
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }.forEach {
            fixedIncomeDao.insert(it)
        }
    }

    private suspend fun downloadShares(currencyIdMap: Map<Int, String>) {
        shareDao.clean()

        oldApi.getShares().map {
            ShareEntity(
                id = uuid4().toString(),
                name = it.name,
                code = it.code,
                currencyId = currencyIdMap[it.currencyId]!!,
                type = getShareType(it.type, it.isGold == 1, it.code).ordinal.toLong(),
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }.forEach {
            shareDao.insert(it)
        }
    }

    private fun getShareType(apiType: String, isGold: Boolean, code: String): ShareType {
        return  if (isGold) {
            ShareType.GOLD
        } else if (code == "BTC-EUR") {
            ShareType.BITCOIN
        } else if (apiType == "Stock") {
            ShareType.Stock
        } else if (apiType == "Reit") {
            ShareType.REIT
        } else {
            throw IllegalArgumentException("Share Type $apiType not found")
        }
    }
}