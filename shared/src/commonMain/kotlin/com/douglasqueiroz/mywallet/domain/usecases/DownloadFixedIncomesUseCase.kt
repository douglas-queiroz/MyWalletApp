package com.douglasqueiroz.mywallet.domain.usecases

import com.benasher44.uuid.uuid4
import com.douglasqueiroz.mywallet.data.model.FixedIncomeEntity
import com.douglasqueiroz.mywallet.domain.enum.FixedIncomeType
import com.douglasqueiroz.mywallet.repository.local.FixedIncomeDao
import com.douglasqueiroz.mywallet.repository.remote.MyWalletOldApi

internal class DownloadFixedIncomesUseCase(
    private val oldApi: MyWalletOldApi,
    private val fixedIncomeDao: FixedIncomeDao,
) {

    suspend fun execute(currencyIdMap: Map<Int, String>): Map<Int, String> {
        val idMap = mutableMapOf<Int, String>()
        fixedIncomeDao.clean()

        oldApi.getFixedIncomes().map {
            val id = uuid4().toString()
            idMap[it.id] = id

            FixedIncomeEntity(
                id = id,
                name = it.name,
                currencyId = currencyIdMap[it.currencyId]!!,
                type = FixedIncomeType.SAVING_ACCOUNT.ordinal.toLong(),
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }.forEach {
            fixedIncomeDao.insert(it)
        }

        return idMap
    }
}