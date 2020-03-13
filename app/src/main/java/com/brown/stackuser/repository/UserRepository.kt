package com.brown.stackuser.repository

import com.brown.stackuser.api.StackOverflowService
import com.brown.stackuser.database.LocalCache

class UserRepository(
    private val service: StackOverflowService,
    private val cache: LocalCache
) {


}
