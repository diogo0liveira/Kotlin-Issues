package com.dao.issues.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created in 26/03/19 21:48.
 *
 * @author Diogo Oliveira.
 */
@Parcelize
data class Issue(
        @Expose
        @SerializedName("title")
        val title: String,
        @Expose
        @SerializedName("number")
        val number: Int,
        @Expose
        @SerializedName("created_at")
        val created: String,
        @Expose
        @SerializedName("body")
        val body: String,
        @Expose
        @SerializedName("state")
        val state: State,
        @Expose
        @SerializedName("user")
        val user: User) : Parcelable
{
    override fun toString(): String
    {
        return title
    }
}