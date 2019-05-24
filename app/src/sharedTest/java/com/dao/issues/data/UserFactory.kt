package com.dao.issues.data

import android.content.Context
import com.dao.issues.R
import com.dao.issues.model.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.*

/**
 * Created in 23/05/19 14:48.
 *
 * @author Diogo Oliveira.
 */
object UserFactory
{
    fun build(
            id: Int,
            name: String = "Name",
            login: String = "",
            avatar: String = "",
            profile: String = "",
            profileLink: String = "",
            location: String? = null,
            blog: String? = null,
            bio: String? = null,
            followers: Int? = null,
            following: Int? = null): User
    {
        return User(id, name, login, avatar, profile, profileLink, location, blog, bio, followers, following)
    }

    fun buildJson(context: Context): User
    {
        val json = context.resources.openRawResource(R.raw.response_200_user)
                .reader().readLines().joinToString("") { it.trim() }

        val moshi =  Moshi.Builder()
                .add(Date::class.java, Rfc3339DateJsonAdapter())
                .add(KotlinJsonAdapterFactory()).build()

        val adapter = moshi.adapter<User>(User::class.java)
        return adapter.fromJson(json)!!
    }
}