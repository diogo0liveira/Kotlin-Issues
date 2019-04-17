package com.dao.issues.util.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Created in 26/03/19 22:33.
 *
 * @author Diogo Oliveira.
 */
object GsonHelper
{
    fun build(): Gson
    {
        return GsonBuilder().create()
    }
}