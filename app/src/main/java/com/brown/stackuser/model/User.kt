package com.brown.stackuser.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
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
    @Json(name = "user_id")
    val id: Long,
    @Json(name = "display_name")
    val name: String,
    @Json(name = "reputation")
    val reputation: Int,
    @Json(name = "location")
    val location: String = "",
    @Json(name = "profile_image")
    val profileImageUrl: String,
    @Json(name = "last_access_date")
    val lastAccessDate: Long,
    // this field is exclusive for moshi
    val favorite: Boolean = false
) : Parcelable


enum class UserFilter {
    ALL,
    FAVORITE
}
