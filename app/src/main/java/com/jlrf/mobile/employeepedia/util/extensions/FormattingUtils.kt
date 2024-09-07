package com.jlrf.mobile.employeepedia.util.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.getYearFromDateString(): String? {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return try {
        LocalDate.parse(this, formatter).year.toString()
    } catch (e: Exception) {
        null
    }
}