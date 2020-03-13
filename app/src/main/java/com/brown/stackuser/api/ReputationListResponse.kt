package com.brown.stackuser.api

import com.brown.stackuser.model.Reputation
import com.squareup.moshi.Json

/**
 * Data class to hold user's reputation responses from stackoverflow API calls.
 */
data class ReputationListResponse(
    val items: List<ReputationResponse>
) {
    fun asModelObjects() = items.map {
        Reputation(
            reputationType = it.reputationType,
            change = it.change,
            creationDate = it.creationDate,
            postId = it.postId,
            userId = it.userId
        )
    }
}

/**
 * Data class to hold user's reputation information
 */
data class ReputationResponse(
    @Json(name = "reputation_history_type") val reputationType: String,
    @Json(name = "reputation_change") val change: Int,
    @Json(name = "creation_date") val creationDate: Long,
    @Json(name = "post_id") val postId: Int,
    @Json(name = "user_id") val userId: Int
)
