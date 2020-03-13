package com.brown.stackuser.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brown.stackuser.repository.UserRepository

class UserListViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val users = repository.users


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
