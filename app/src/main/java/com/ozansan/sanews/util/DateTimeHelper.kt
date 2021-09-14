package com.ozansan.sanews.util

import android.icu.text.SimpleDateFormat
import java.util.*

class DateTimeHelper {

    companion object {
        private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)

        fun getDateString(time: Long): String = simpleDateFormat.format(time * 1000L)

        fun getDateString(time: Int): String = simpleDateFormat.format(time * 1000L)
    }

}