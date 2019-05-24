package com.dao.issues.util

import android.content.Context
import androidx.annotation.RawRes

/**
 * Created in 24/05/19 11:31.
 *
 * @author Diogo Oliveira.
 */
object ResponseBodyParser
{
    fun from(context: Context, @RawRes json: Int): String
    {
        return context.resources.openRawResource(json)
                .reader().readLines().joinToString("") { it.trim() }
    }
}