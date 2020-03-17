package com.brown.stackuser.api

import com.brown.stackuser.model.User

/**
 * Data class to hold users list responses from stackoverflow API calls.
 */
data class UserListResponse(
    val items: List<User>
)
