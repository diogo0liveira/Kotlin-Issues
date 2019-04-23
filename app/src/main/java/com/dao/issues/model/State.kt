package com.dao.issues.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Created in 26/03/19 21:49.
 *
 * @author Diogo Oliveira.
 */
@Parcelize
enum class State : Parcelable
{
    @Json(name = "all")
    ALL
    {
        override fun value(): String
        {
            return "all"
        }
    },
    @Json(name = "open")
    OPEN
    {
        override fun value(): String
        {
            return "open"
        }
    },

    @Json(name = "closed")
    CLOSED
    {
        override fun value(): String
        {
            return "closed"
        }
    };

    abstract fun value(): String
}