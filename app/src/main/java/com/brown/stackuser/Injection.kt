package com.brown.stackuser

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.brown.stackuser.api.StackOverflowService
import com.brown.stackuser.database.LocalCache
import com.brown.stackuser.database.UserDatabase
import com.brown.stackuser.repository.UserRepository
import com.brown.stackuser.ui.overview.UserListViewModel
import java.util.concurrent.Executors

/**
 * Class that handles object creation.
 */
object Injection {

    /**
     * Creates an instance of [LocalCache] based on the database DAO.
     */
    private fun provideCache(context: Context): LocalCache {
        val database = UserDatabase.getInstance(context)
        return LocalCache(database.userDao, Executors.newSingleThreadExecutor())
    }

    /**
     * Creates an instance of [UserRepository] based on the [StackOverflowError] and a
     * [LocalCache]
     */
    private fun provideUserRepository(context: Context): UserRepository {
        return UserRepository(
            StackOverflowService.create(),
            provideCache(context),
            Executors.newSingleThreadExecutor()
        )
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [UserListViewModel] objects.
     */
    fun provideUserListViewModelFactory(context: Context): ViewModelProvider.Factory {
        return UserListViewModel.Factory(provideUserRepository(context))
    }

}
