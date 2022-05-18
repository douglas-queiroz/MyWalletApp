package com.douglasqueiroz.mywallet.domain.usecases

import com.benasher44.uuid.uuid4
import com.douglasqueiroz.mywallet.data.model.FixedIncomeEntity
import com.douglasqueiroz.mywallet.data.model.ShareEntity
import com.douglasqueiroz.mywallet.domain.dto.AssetDto
import com.douglasqueiroz.mywallet.domain.enum.AssetType
import com.douglasqueiroz.mywallet.domain.enum.FixedIncomeType
import com.douglasqueiroz.mywallet.domain.enum.ShareType
import com.douglasqueiroz.mywallet.repository.local.FixedIncomeDao
import com.douglasqueiroz.mywallet.repository.local.ShareDao
import com.douglasqueiroz.mywallet.util.DateUtil

interface InsertAssetUseCase {

    suspend fun execute(
        assetDto: AssetDto,
        assetType: AssetType,
        fixedIncomeType: FixedIncomeType?,
        shareType: ShareType?
    )
}

internal class InsertAssetUseCaseImpl(
    private val fixedIncomeDao: FixedIncomeDao,
    private val shareDao: ShareDao,
    private val dateUtil: DateUtil
): InsertAssetUseCase {

    override suspend fun execute(
        assetDto: AssetDto,
        assetType: AssetType,
        fixedIncomeType: FixedIncomeType?,
        shareType: ShareType?
    ) {
        when(assetType) {
            AssetType.FixedIncome -> saveFixedIncomeAsset(assetDto, fixedIncomeType)
            AssetType.Share -> saveShareAsset(assetDto, shareType)
        }
    }

    private suspend fun saveShareAsset(assetDto: AssetDto, shareType: ShareType?) {

        val type = shareType ?: return

        ShareEntity(
            id = uuid4().toString(),
            name = assetDto.name,
            code = assetDto.code,
            currencyId = assetDto.currency.id,
            type = type.ordinal.toLong(),
            createdAt = dateUtil.getDateTimeNow().toString(),
            updatedAt = dateUtil.getDateTimeNow().toString()
        ).also {
            shareDao.insert(it)
        }

    }

    private suspend fun saveFixedIncomeAsset(assetDto: AssetDto, fixedIncomeType: FixedIncomeType?) {

        val type = fixedIncomeType ?: return

        FixedIncomeEntity(
            id = uuid4().toString(),
            name = assetDto.name,
            currencyId = assetDto.currency.id,
            type = type.ordinal.toLong(),
            createdAt = dateUtil.getDateTimeNow().toString(),
            updatedAt = dateUtil.getDateTimeNow().toString()
        ).also {
            fixedIncomeDao.insert(it)
        }
    }
}