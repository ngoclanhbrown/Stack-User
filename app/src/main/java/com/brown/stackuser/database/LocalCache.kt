package com.brown.stackuser.database

import com.brown.stackuser.model.User
import java.util.concurrent.Executor

/**
 * Class that handles the DAO local data source. This ensures that methods are triggered on the
 * correct executor.
 */
class LocalCache(
    private val userDao: UserDao,
    private val ioExecutor: Executor
) {

    /**
     * Insert list of users to the database, if a user is existed, update the user instead.
     * The favorite field is exclusive while update
     * Work on a background thread
     */
    fun upsertUsers(users: List<User>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            userDao.upsert(users)
            insertFinished()
        }
    }

    /**
     * Update favorite status of a user to the database, on a background thread
     */
    fun updateFavoriteUser(userId: Long, favorite: Boolean) {
        ioExecutor.execute {
            userDao.updateFavoriteUser(userId, favorite)
        }
    }

    fun getUsers() = userDao.getUsers()

    fun getFavoriteUsers() = userDao.getFavoriteUsers()

}
