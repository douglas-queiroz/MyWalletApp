package com.douglasqueiroz.mywallet

actual object Logger {

    actual fun d(message: String) {
        println(message)
    }
}