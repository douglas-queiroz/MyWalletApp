package com.douglasqueiroz.mywallet.domain.dto

import com.douglasqueiroz.mywallet.domain.enum.FixedIncomeType
import com.douglasqueiroz.mywallet.domain.enum.ShareType

data class OverallReportDto (
    val name: String,
    val total: Double,
    val percentage: Double,
    val shareType: ShareType?,
    val fixedIncomeType: FixedIncomeType?
)