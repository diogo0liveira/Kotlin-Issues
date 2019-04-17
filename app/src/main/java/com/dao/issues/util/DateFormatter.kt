package com.dao.issues.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created in 17/04/19 13:43.
 *
 * @author Diogo Oliveira.
 */
private const val PATTERN_PARSER = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val PATTERN_FORMAT = "EEE, MMM d, yy"

object DateFormatter
{
    private val formatter = SimpleDateFormat(PATTERN_PARSER, Locale.ENGLISH)

    private fun parser(date: String): Date
    {
        formatter.applyPattern(PATTERN_PARSER)
        return formatter.parse(date)
    }

    private fun format(date: Date): String
    {
        formatter.applyPattern(PATTERN_FORMAT)
        return formatter.format(date)
    }

    @JvmStatic
    fun format(date: String): String
    {
        return format(parser(date))
    }
}