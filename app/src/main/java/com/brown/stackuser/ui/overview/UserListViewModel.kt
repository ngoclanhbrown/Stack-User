package com.brown.stackuser.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.toLiveData
import com.brown.stackuser.database.UserDao
import com.brown.stackuser.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserListViewModel(
    private val userDao: UserDao
) : ViewModel() {

    val usersDataSource = userDao.getUsers()
    val users = usersDataSource.toLiveData(
        25
    )

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userDao.upsert(dummyData)
            }
        }
    }


    fun updateFavorite(user: User) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userDao.updateFavoriteUser(user.id, !user.favorite)
            }
        }
    }


    class Factory(
        private val userDao: UserDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserListViewModel(userDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}


val dummyData = listOf<User>(
    User(
        id = 11683,
        lastAccessDate = 1583397983,
        reputation = 1167675,
        location = "Reading, United Kingdom",
        profileImageUrl = "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=128&d=identicon&r=PG",
        name = "Jon Skeet",
        favorite = false
    ),
    User(
        id = 4243,
        lastAccessDate = 1583397721,
        reputation = 934280,
        location = "France",
        profileImageUrl = "https://www.gravatar.com/avatar/7aa22372b695ed2b26052c340f9097eb?s=128&d=identicon&r=PG",
        name = "VonC",
        favorite = false
    ),
    User(
        id = 1165580,
        lastAccessDate = 1583377975,
        reputation = 924235,
        location = "New York, United States",
        profileImageUrl = "https://www.gravatar.com/avatar/e514b017977ebf742a418cac697d8996?s=128&d=identicon&r=PG",
        name = "Gordon Linoff",
        favorite = false
    ),
    User(
        id = 52822,
        lastAccessDate = 1583362755,
        reputation = 917081,
        location = "Willemstad, Cura&#231;ao",
        profileImageUrl = "https://www.gravatar.com/avatar/89927e2f4bde24991649b353a37678b9?s=128&d=identicon&r=PG",
        name = "BalusC",
        favorite = false
    ),
    User(
        id = 14332,
        lastAccessDate = 1583354310,
        reputation = 901785,
        location = "Sofia, Bulgaria",
        profileImageUrl = "https://www.gravatar.com/avatar/e3a181e9cdd4757a8b416d93878770c5?s=128&d=identicon&r=PG",
        name = "Darin Dimitrov",
        favorite = false
    ),
    User(
        id = 11975,
        lastAccessDate = 1583396388,
        reputation = 851612,
        location = "Forest of Dean, United Kingdom",
        profileImageUrl = "https://i.stack.imgur.com/CrVFH.png?s=128&g=1",
        name = "Marc Gravell",
        favorite = false
    ),
    User(
        id = 39846,
        lastAccessDate = 1583364652,
        reputation = 838151,
        location = "Pennsylvania, United States",
        profileImageUrl = "https://i.stack.imgur.com/wDnd8.png?s=128&g=1",
        name = "CommonsWare",
        favorite = false
    ),
    User(
        id = 9266,
        lastAccessDate = 1583396431,
        reputation = 831353,
        location = "Madison, WI",
        profileImageUrl = "https://i.stack.imgur.com/Cii6b.png?s=128&g=1",
        name = "Hans Passant",
        favorite = false
    ),
    User(
        id = 35417,
        lastAccessDate = 1583398057,
        reputation = 796575,
        location = "Cambridge, UK",
        profileImageUrl = "https://www.gravatar.com/avatar/24780fb6df85a943c7aea0402c843737?s=128&d=identicon&r=PG",
        name = "Martijn Pieters",
        favorite = false
    ),
    User(
        id = 52616,
        lastAccessDate = 1583397972,
        reputation = 776624,
        location = "United Kingdom",
        profileImageUrl = "https://www.gravatar.com/avatar/ca3e484c121268e4c8302616b2395eb9?s=128&d=identicon&r=PG",
        name = "T.J. Crowder",
        favorite = true
    )
)