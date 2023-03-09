package com.douglasqueiroz.mywallet.android.di

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.di.ApiModule
import com.douglasqueiroz.mywallet.di.DaoModule
import com.douglasqueiroz.mywallet.di.DomainModule
import com.douglasqueiroz.mywallet.di.UtilModule

class MyWalletModules(
    databaseDriverFactory: DatabaseDriverFactory
) {

    val modules = listOf(
        ViewModelModule.module,
        DomainModule.module,
        DaoModule(databaseDriverFactory).module,
        ApiModule.module,
        UtilModule.module,
        AppUtilModule.module
    )
}
