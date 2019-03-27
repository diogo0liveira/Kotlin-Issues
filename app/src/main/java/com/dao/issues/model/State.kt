package com.dao.issues.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created in 26/03/19 21:49.
 *
 * @author Diogo Oliveira.
 */
@Parcelize
enum class State : Parcelable
{
    @SerializedName("open")
    OPEN
    {
        override fun value(): String
        {
            return "open"
        }
    },

    @SerializedName("close")
    CLOSE
    {
        override fun value(): String
        {
            return "close"
        }
    };

    abstract fun value(): String
}