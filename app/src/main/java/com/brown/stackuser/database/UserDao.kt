package com.brown.stackuser.database

import androidx.paging.DataSource
import androidx.room.*
import com.brown.stackuser.model.User
import timber.log.Timber

@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertUsers(users: List<User>): List<Long>

    @Query("SELECT * FROM User ORDER BY reputation DESC")
    abstract fun getUsers(): DataSource.Factory<Int, User>

    @Query("SELECT * FROM User WHERE favorite = 1 ORDER BY reputation DESC")
    abstract fun getFavoriteUsers(): DataSource.Factory<Int, User>

    @Query("UPDATE User SET name = :name, reputation = :reputation, " +
            "location = :location, profileImageUrl = :profileImageUrl, " +
            "lastAccessDate = :lastAccessDate WHERE id = :userId")
    abstract fun updateUser(
        userId: Long, name: String, reputation: Int,
        location: String, profileImageUrl: String, lastAccessDate: Long
    )

    @Query("UPDATE User SET favorite = :favorite WHERE id = :userId")
    abstract fun updateFavoriteUser(userId: Long, favorite: Boolean)


    @Transaction
    open fun upsert(users: List<User>) {
        val insertResult = insertUsers(users)
        val updateList = mutableListOf<User>()

        for (i in insertResult.indices) {
            if (insertResult[i] == -1L)
                updateList.add(users[i])
        }

        for (user in updateList) {
            updateUser(
                userId = user.id,
                name = user.name,
                reputation = user.reputation,
                location = user.location,
                profileImageUrl = user.profileImageUrl,
                lastAccessDate = user.lastAccessDate
            )
        }

        Timber.i("Inserted ${users.size - updateList.size} records,")
        Timber.i("Updated ${updateList.size} records")
    }

}
