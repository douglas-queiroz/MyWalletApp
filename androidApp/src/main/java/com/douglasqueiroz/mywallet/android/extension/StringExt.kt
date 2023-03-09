package com.douglasqueiroz.mywallet.android.extension

import com.douglasqueiroz.mywallet.android.util.Constants.DATE_FORMAT_DD_MM_YY
import com.douglasqueiroz.mywallet.android.util.Constants.DATE_FORMAT_YYYY_MM_DD
import com.douglasqueiroz.mywallet.android.util.Constants.DATE_REGEX_YYYY_MM_DD
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate() : Date? = SimpleDateFormat(DATE_FORMAT_DD_MM_YY).parse(this)

fun String.isDate() = this.matches(Regex(DATE_REGEX_YYYY_MM_DD))

fun String.addMask(mask: String) : String {

    val symbol = '#'
    var result = ""
    var stringIndex = 0

    mask.forEach { char ->

        if (stringIndex >= this.length) {
            return@forEach
        }

        if (char == symbol) {
            result += this[stringIndex]
            stringIndex++
        } else {
            result += char
        }
    }

    return result
}