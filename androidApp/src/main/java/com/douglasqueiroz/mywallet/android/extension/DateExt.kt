package com.douglasqueiroz.mywallet.android.extension

import com.douglasqueiroz.mywallet.android.util.Constants.DATE_FORMAT_YYYY_MM_DD
import java.text.SimpleDateFormat
import java.util.*

fun Date.toFormattedString(format: String = DATE_FORMAT_YYYY_MM_DD) : String {
    val dateFormat = SimpleDateFormat(format)
    return dateFormat.format(this)
}