package com.douglasqueiroz.mywallet.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DateUtil {

    fun getDateTimeNow(): LocalDateTime {
        val now = Clock.System.now()
        return now.toLocalDateTime(TimeZone.currentSystemDefault())
    }
}