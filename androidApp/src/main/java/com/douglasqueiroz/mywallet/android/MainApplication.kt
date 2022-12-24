package com.douglasqueiroz.mywallet.android

import android.app.Application
import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.android.di.MyWalletModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(MyWalletModules(DatabaseDriverFactory(this@MainApplication)).modules)
        }
    }
}