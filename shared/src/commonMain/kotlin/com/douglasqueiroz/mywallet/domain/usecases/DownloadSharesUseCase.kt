package com.douglasqueiroz.mywallet.domain.usecases

import com.benasher44.uuid.uuid4
import com.douglasqueiroz.mywallet.data.model.ShareEntity
import com.douglasqueiroz.mywallet.domain.enum.ShareType
import com.douglasqueiroz.mywallet.repository.local.ShareDao
import com.douglasqueiroz.mywallet.repository.remote.MyWalletOldApi

internal class DownloadSharesUseCase(
    private val oldApi: MyWalletOldApi,
    private val shareDao: ShareDao
) {

    suspend fun execute(currencyIdMap: Map<Int, String>): Map<Int, String> {
        val idMap = mutableMapOf<Int, String>()
        shareDao.clean()

        oldApi.getShares().map {
            val id = uuid4().toString()
            idMap[it.id] = id

            ShareEntity(
                id = id,
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

        return idMap
    }

    private fun getShareType(apiType: String, isGold: Boolean, code: String): ShareType {
        return when {
            isGold -> ShareType.GOLD
            code == "BTC-EUR" -> ShareType.BITCOIN
            apiType == "Stock" -> ShareType.Stock
            apiType == "Reit" -> ShareType.REIT
            else -> throw IllegalArgumentException("Share Type $apiType not found")
        }
    }
}