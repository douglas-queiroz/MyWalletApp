package com.douglasqueiroz.mywallet.di

import com.douglasqueiroz.mywallet.repository.remote.QuotationAPI
import org.koin.dsl.module

object ApiModule {

    val module = module {
        factory { QuotationAPI() }
    }
}