package com.brown.stackuser.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Immutable model class for a StackOverflow user that holds all the information about a user.
 * This class defines the Room User table, where the user [id] is the primary key.
 * It is also be parcelized to be able to send via Bundle
 */
@Entity
@Parcelize
data class User(
    @PrimaryKey
    val id: Long,
    val name: String,
    val reputation: Int,
    val location: String,
    val profileImageUrl: String,
    val lastAccessDate: Long,
    val favorite: Boolean
) : Parcelable


enum class UserFilter {
    ALL,
    FAVORITE
}
