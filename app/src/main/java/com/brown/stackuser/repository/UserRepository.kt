package com.brown.stackuser.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.brown.stackuser.api.StackOverflowService
import com.brown.stackuser.database.LocalCache
import com.brown.stackuser.model.Reputation
import com.brown.stackuser.model.User
import java.util.concurrent.Executor

class UserRepository(
    private val service: StackOverflowService,
    private val cache: LocalCache,
    private val executor: Executor
) {

    companion object {
        const val DATABASE_PAGE_SIZE = 25
    }


    fun getAllUser(): Pair<LiveData<PagedList<User>>, LiveData<String>> {
        val callback = UserBoundaryCallback(service, cache)
        val data = cache.getUsers().toLiveData(
            DATABASE_PAGE_SIZE,
            boundaryCallback = callback,
            fetchExecutor = executor
        )

        return Pair(data, callback.networkError)
    }


    val favoriteUsers = cache.getFavoriteUsers().toLiveData(
        DATABASE_PAGE_SIZE,
        fetchExecutor = executor
    )


    fun getUserReputation(userId: Long): Pair<LiveData<PagedList<Reputation>>, LiveData<String>> {
        val callback = ReputationBoundaryCallback(service, cache, userId)
        val data = cache.getUserReputation(userId).toLiveData(
            DATABASE_PAGE_SIZE,
            boundaryCallback = callback,
            fetchExecutor = executor
        )

        return Pair(data, callback.networkError)
    }


    fun updateFavoriteUser(userId: Long, favorite: Boolean) {
        cache.updateFavoriteUser(userId, favorite)
    }

}
