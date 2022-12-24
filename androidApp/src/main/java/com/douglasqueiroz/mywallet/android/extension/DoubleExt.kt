package com.douglasqueiroz.mywallet.android.extension

import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.format(format: String = "#.##"): String {
    val decimalFormat = DecimalFormat("#,###.##")
    decimalFormat.roundingMode = RoundingMode.DOWN

    return decimalFormat.format(this)
}