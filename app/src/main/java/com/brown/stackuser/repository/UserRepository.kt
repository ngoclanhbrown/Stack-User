package com.brown.stackuser.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.brown.stackuser.api.StackOverflowService
import com.brown.stackuser.database.LocalCache
import com.brown.stackuser.model.Reputation
import java.util.concurrent.Executor

class UserRepository(
    private val service: StackOverflowService,
    private val cache: LocalCache,
    private val executor: Executor
) {

    companion object {
        const val DATABASE_PAGE_SIZE = 25
    }

    val allUsers = cache.getUsers().toLiveData(
        DATABASE_PAGE_SIZE,
        boundaryCallback = UserBoundaryCallback(service, cache),
        fetchExecutor = executor
    )


    val favoriteUsers = cache.getFavoriteUsers().toLiveData(
        DATABASE_PAGE_SIZE,
        fetchExecutor = executor
    )


    fun getUserReputation(userId: Long): LiveData<PagedList<Reputation>> {
        return cache.getUserReputation(userId).toLiveData(
            DATABASE_PAGE_SIZE,
            boundaryCallback = ReputationBoundaryCallback(service, cache, userId),
            fetchExecutor = executor
        )
    }


    fun updateFavoriteUser(userId: Long, favorite: Boolean) {
        cache.updateFavoriteUser(userId, favorite)
    }

}
