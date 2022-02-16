package com.douglasqueiroz.mywallet.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.douglasqueiroz.mywallet.Greeting
import android.widget.TextView
import com.douglasqueiroz.mywallet.DatabaseDriverFactory
import com.douglasqueiroz.mywallet.di.DomainModule
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
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

        val test = DomainModule(DatabaseDriverFactory(this)).getLoadDatabaseUseCase()

        val mainScope = MainScope()

        mainScope.launch {
            test.execute()
        }
    }
}
