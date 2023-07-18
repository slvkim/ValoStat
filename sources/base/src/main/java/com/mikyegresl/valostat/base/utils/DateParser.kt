package com.mikyegresl.valostat.base.utils

import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

fun String?.convertDate(): String? =
    this?.let {
        val localDate = LocalDate.parse(it, DateTimeFormatter.ISO_DATE_TIME)
        localDate.format(DateTimeFormatter.ofPattern("dd/MM/yy"))
    }

fun String?.parseDate(): String? =
    this?.let {
        val from = LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
        val to = LocalDate.now()

        val period = Period.between(from, to)
        val years = period.years
        val months = period.months
        val days = period.days

        if (years > 0) {
            if (years > 1) {
                "$years years ago"
            } else {
                "1 year ago"
            }
        }
        else if (months > 0) {
            if (months > 1) {
                "$months months ago"
            } else {
                "1 month ago"
            }
        }
        else if (days > 0) {
            if (days > 1) {
                "$days days ago"
            } else {
                "1 day ago"
            }
        }
        else "today"
    }
