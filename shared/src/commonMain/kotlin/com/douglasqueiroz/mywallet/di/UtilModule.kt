package com.douglasqueiroz.mywallet.di

import com.douglasqueiroz.mywallet.util.DateUtil
import org.koin.dsl.module

object UtilModule {

    val module = module {
        factory { DateUtil() }
    }
}