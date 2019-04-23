package com.dao.issues.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * Created in 27/03/19 11:48.
 *
 * @author Diogo Oliveira.
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class User(
        @Json(name = "id")
        val id: Int,
        @Json(name = "name")
        val name: String? = "",
        @Json(name = "login")
        val login: String,
        @Json(name = "avatar_url")
        val avatar: String,
        @Json(name = "url")
        val profile: String,
        @Json(name = "html_url")
        val profileLink: String,
        @Json(name = "location")
        val location: String?,
        @Json(name = "blog")
        val blog: String?,
        @Json(name = "bio")
        val bio: String?,
        @Json(name = "followers")
        val followers: Int?,
        @Json(name = "following")
        val following: Int?): Parcelable
{
    override fun toString(): String
    {
        return login
    }
}