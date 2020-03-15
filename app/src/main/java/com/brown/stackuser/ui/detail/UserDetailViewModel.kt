package com.brown.stackuser.ui.detail

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brown.stackuser.model.User
import com.brown.stackuser.repository.UserRepository

class UserDetailViewModel(
    private val repository: UserRepository,
    val user: User
) : ViewModel() {

    val reputationList = repository.getUserReputation(user.id)

    val showProgressBar = Transformations.map(reputationList) {
        it.size == 0
    }

    class Factory(
        private val repository: UserRepository,
        private val user: User
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserDetailViewModel(repository, user) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
