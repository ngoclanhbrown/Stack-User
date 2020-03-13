package com.brown.stackuser.ui.overview

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.brown.stackuser.model.User
import com.brown.stackuser.model.UserFilter
import com.brown.stackuser.repository.UserRepository

class UserListViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val allUsers = repository.allUsers
    private val favoriteUsers = repository.favoriteUsers

    val users = MediatorLiveData<PagedList<User>>()

    private var currentFilter = UserFilter.ALL

    init {
        users.addSource(allUsers) {
            if (currentFilter == UserFilter.ALL) {
                it?.let {
                    users.value = it
                }
            }
        }

        users.addSource(favoriteUsers) {
            if (currentFilter == UserFilter.FAVORITE) {
                it?.let {
                    users.value = it
                }
            }
        }
    }


    fun updateFavoriteUser(user: User) {
        repository.updateFavoriteUser(user.id, !user.favorite)
    }


    fun updateFilter(newFilter: UserFilter) {
        if (currentFilter != newFilter) {
            when (newFilter) {
                UserFilter.ALL -> allUsers.value?.let { users.value = it }
                UserFilter.FAVORITE -> favoriteUsers.value?.let { users.value = it }
            }
            currentFilter = newFilter
        }
    }


    class Factory(
        private val repository: UserRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}
