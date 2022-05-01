package com.douglasqueiroz.mywallet

import kotlinx.datetime.Instant
import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    fun Instant.Companion.dummy(): Nothing = TODO()
}