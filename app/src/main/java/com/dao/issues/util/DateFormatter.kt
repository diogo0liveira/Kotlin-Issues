package com.dao.issues.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created in 17/04/19 13:43.
 *
 * @author Diogo Oliveira.
 */
private const val PATTERN_FORMAT = "EEE, MMM d, yy"

object DateFormatter
{
    private val formatter = SimpleDateFormat(PATTERN_FORMAT, Locale.ENGLISH)

    @JvmStatic
    fun format(date: Date): String
    {
        formatter.applyPattern(PATTERN_FORMAT)
        return formatter.format(date)
    }
}