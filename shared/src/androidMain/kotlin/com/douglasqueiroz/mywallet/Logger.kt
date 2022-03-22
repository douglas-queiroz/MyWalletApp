package com.douglasqueiroz.mywallet

import android.util.Log

actual object Logger {

    actual fun d(message: String) {
        Log.i("Douglas", message)
    }
}