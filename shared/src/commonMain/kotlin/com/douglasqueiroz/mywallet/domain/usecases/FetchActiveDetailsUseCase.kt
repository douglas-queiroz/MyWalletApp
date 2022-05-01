package com.douglasqueiroz.mywallet.domain.usecases

import com.douglasqueiroz.mywallet.domain.enum.FixedIncomeType
import com.douglasqueiroz.mywallet.domain.enum.ShareType

interface FetchActiveDetailsUseCase {

}

class FetchActiveDetailsUseCaseImp {

    fun execute(
        id: String,
        shareType: ShareType? ,
        fixedIncomeType: FixedIncomeType?
    ) {


    }
}