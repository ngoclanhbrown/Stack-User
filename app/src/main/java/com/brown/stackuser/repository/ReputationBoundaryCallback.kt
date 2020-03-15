package com.brown.stackuser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.brown.stackuser.api.StackOverflowService
import com.brown.stackuser.api.fetchReputation
import com.brown.stackuser.database.LocalCache
import com.brown.stackuser.model.Reputation

class ReputationBoundaryCallback(
    private val service: StackOverflowService,
    private val cache: LocalCache,
    private val userId: Long
) : PagedList.BoundaryCallback<Reputation>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedSize = 1

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    // handle network error
    private val _networkError = MutableLiveData<String>()
    val networkError: LiveData<String> = _networkError


    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Reputation) {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) {
            return
        }
        isRequestInProgress = true

        fetchReputation(service, userId, lastRequestedSize, NETWORK_PAGE_SIZE,
            {
                cache.insertUserReputation(it) {
                    lastRequestedSize++
                    isRequestInProgress = false
                }
            },
            {
                _networkError.postValue(it)
                isRequestInProgress = false
            }
        )
    }
}
