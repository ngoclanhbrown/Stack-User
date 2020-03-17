package com.brown.stackuser.api

import com.brown.stackuser.model.Reputation

/**
 * Data class to hold user's reputation responses from stackoverflow API calls.
 */
data class ReputationListResponse(
    val items: List<Reputation>
)
