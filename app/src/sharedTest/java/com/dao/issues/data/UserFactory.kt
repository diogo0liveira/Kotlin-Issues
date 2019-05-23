package com.dao.issues.data

import com.dao.issues.model.User

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
}