package com.dao.issues.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created in 27/03/19 22:42.
 *
 * @author Diogo Oliveira.
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class Comment(
        @Json(name = "created_at")
        val created: Date,
        @Json(name = "author_association")
        val author: String,
        @Json(name = "body")
        val body: String,
        @Json(name = "user")
        val user: User) : Parcelable
{
    override fun toString(): String
    {
        return body
    }
}