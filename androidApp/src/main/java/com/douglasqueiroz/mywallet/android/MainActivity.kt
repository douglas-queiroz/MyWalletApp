package com.douglasqueiroz.mywallet.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.douglasqueiroz.mywallet.Greeting
import android.widget.TextView
import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.di.DomainModule
import com.douglasqueiroz.mywallet.domain.enum.ShareType
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.time.Instant
import java.util.*
import kotlin.coroutines.CoroutineContext

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()

        val domain = DomainModule(DatabaseDriverFactory(this))

        val download = domain.getLoadDatabaseUseCase()
        val calculate = domain.getCalculateOverallReportUseCase()
        val collectQuotationsUseCase = domain.getCollectionQuotationsUseCase()
        val fetchShare = domain.getFetchShareByTypeUseCase()
        val addTransactionUseCase = domain.getAddTransactionUseCase()

        val mainScope = MainScope()

        mainScope.launch {
            val share = fetchShare.execute(ShareType.Stock).first()




//            addTransactionUseCase.execute(
//                active = share,
//                price = 10.5f
//            )
        }
    }
}
