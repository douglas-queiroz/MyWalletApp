package com.douglasqueiroz.mywallet.android.di

import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.di.DaoModule
import com.douglasqueiroz.mywallet.di.DomainModule

class MyWalletModules(
    databaseDriverFactory: DatabaseDriverFactory
) {

    val modules = listOf(
        ViewModelModule.module,
        DomainModule.module,
        DaoModule(databaseDriverFactory).module
    )
}
