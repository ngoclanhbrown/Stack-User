package com.brown.stackuser.api

import com.brown.stackuser.model.User
import com.squareup.moshi.Json

/**
 * Data class to hold users list responses from stackoverflow API calls.
 */
data class UserListResponse(
    @Json(name = "items")
    val usersList: List<UserResponse>
) {
    fun asModelObjects() = usersList.map {
        User(
            id = it.id,
            name = it.name,
            reputation = it.reputation,
            location = it.location ?: "",
            profileImageUrl = it.profileImageUrl,
            lastAccessDate = it.lastAccessDate,
            favorite = false
        )
    }
}

/**
 * Data class to hold user's information.
 */
data class UserResponse(
    @Json(name = "user_id")
    val id: Long,
    @Json(name = "display_name")
    val name: String,
    @Json(name = "reputation")
    val reputation: Int,
    @Json(name = "location")
    val location: String?,
    @Json(name = "profile_image")
    val profileImageUrl: String,
    @Json(name = "last_access_date")
    val lastAccessDate: Long
)
