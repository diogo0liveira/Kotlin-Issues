package com.dao.issues.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parceler
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
        @SerializedName("state")
        val state: State) : Parcelable
{
    override fun toString(): String
    {
        return title
    }

    private companion object : Parceler<Issue>
    {
        override fun create(parcel: Parcel): Issue
        {
            return Issue(
                    parcel.readString() ?: "",
                     State.valueOf(parcel.readString() ?: ""))
        }

        override fun Issue.write(parcel: Parcel, flags: Int)
        {
            parcel.writeString(title)
            parcel.writeString(state.name)
        }
    }
}