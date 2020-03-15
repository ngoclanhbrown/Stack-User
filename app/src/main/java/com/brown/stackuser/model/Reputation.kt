package com.brown.stackuser.model

import androidx.room.Entity
import androidx.room.ForeignKey

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
    val reputationType: String,
    val change: Int,
    val creationDate: Long,
    val postId: Int,
    val userId: Int
)
