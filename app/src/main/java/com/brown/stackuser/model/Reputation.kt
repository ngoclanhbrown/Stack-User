package com.brown.stackuser.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.squareup.moshi.Json

/**
 * Immutable model class for a StackOverflow user that holds user's reputation information.
 * This class also defines the Room UserReputation table,
 * where the [userId], [postId] and [creationDate] is the primary keys.
 */
@Entity(
    tableName = "UserReputation",
    primaryKeys = ["userId", "postId", "creationDate"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Reputation(
    @Json(name = "reputation_history_type")
    val reputationType: String,
    @Json(name = "reputation_change")
    val change: Int,
    @Json(name = "creation_date")
    val creationDate: Long,
    @Json(name = "post_id")
    val postId: Int,
    @Json(name = "user_id")
    val userId: Int
)
