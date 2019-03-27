package com.dao.issues.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created in 27/03/19 11:48.
 *
 * @author Diogo Oliveira.
 */
@Parcelize
data class User(
        @Expose
        @SerializedName("id")
        val id: Int,
        @Expose
        @SerializedName("name")
        private val _name: String? = "",
        @Expose
        @SerializedName("login")
        val login: String,
        @Expose
        @SerializedName("avatar_url")
        val avatar: String,
        @Expose
        @SerializedName("url")
        val profile: String): Parcelable
{
    val name
        get () = _name ?: ""

    override fun toString(): String
    {
        return login
    }
}