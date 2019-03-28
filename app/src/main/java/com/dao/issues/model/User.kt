package com.dao.issues.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created in 27/03/19 11:48.
 *
 * @author Diogo Oliveira.
 */
@Parcelize
data class User(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        private val _name: String? = "",
        @SerializedName("login")
        val login: String,
        @SerializedName("avatar_url")
        val avatar: String,
        @SerializedName("url")
        val profile: String,
        @SerializedName("html_url")
        val profileLink: String,
        @SerializedName("location")
        private val _location: String?,
        @SerializedName("blog")
        private val _blog: String?,
        @SerializedName("bio")
        private val _bio: String?,
        @SerializedName("followers")
        val followers: Int,
        @SerializedName("following")
        val following: Int): Parcelable
{
    val name
        get () = _name ?: ""

    val location
        get () = _location ?: ""

    val blog
        get () = _blog ?: ""

    val bio
        get () = _bio ?: ""

    override fun toString(): String
    {
        return login
    }
}