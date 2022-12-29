package com.douglasqueiroz.mywallet.android.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.douglasqueiroz.mywallet.domain.usecases.CollectQuotationsUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class QuotationWorker(
    appContext: Context, workerParams: WorkerParameters
): CoroutineWorker(appContext, workerParams), KoinComponent {

    private val collectQuotationsUseCase: CollectQuotationsUseCase by inject()

    override suspend fun doWork(): Result {
        collectQuotationsUseCase.execute()
        return Result.success()
    }
}