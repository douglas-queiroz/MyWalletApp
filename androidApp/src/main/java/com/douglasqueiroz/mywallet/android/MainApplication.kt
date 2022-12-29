package com.douglasqueiroz.mywallet.android

import android.app.Application
import androidx.work.*
import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.android.di.MyWalletModules
import com.douglasqueiroz.mywallet.android.workmanager.QuotationWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.*
import java.util.concurrent.TimeUnit

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(MyWalletModules(DatabaseDriverFactory(this@MainApplication)).modules)
        }

        setQuotationWorker()
    }

    private fun setQuotationWorker() {

        val workerParameters = PeriodicWorkRequestBuilder<QuotationWorker>(12, TimeUnit.HOURS)
            .setConstraints(
                Constraints
                    .Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED).build()
            ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "QuotationWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workerParameters
        )
    }
}