package com.douglasqueiroz.mywallet.util

import kotlinx.datetime.*

class DateUtil {

    fun getDateTimeNow(): LocalDateTime {
        val now = Clock.System.now()
        return now.toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun timeStampToString(timeStamp: Long): String {
        return Instant.fromEpochMilliseconds(timeStamp * 1000).toString()
    }
}