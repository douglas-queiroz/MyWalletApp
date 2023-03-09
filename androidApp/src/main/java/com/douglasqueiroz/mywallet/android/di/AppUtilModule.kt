package com.douglasqueiroz.mywallet.android.di

import com.douglasqueiroz.mywallet.android.util.StringResUtil
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AppUtilModule {

    var module = module {
        factory {
            StringResUtil(context = androidContext())
        }
    }
}