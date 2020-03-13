package com.brown.stackuser.api

import com.brown.stackuser.model.Reputation
import com.brown.stackuser.model.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import timber.log.Timber

private const val BASE_URL = "https://api.stackexchange.com/2.2/"
private const val STACKOVERFLOW = "stackoverflow"

/**
 * Fetch StackOverflow users list
 * Trigger a request to the StackOverflow API with the following params:
 * @param page request page index
 * @param pageSize number of users to be returned by the StackOverflow API per page
 *
 * The result of the request is handled by the implementation of the functions passed as params
 * @param onSuccess function that defines how to handle the list of users received
 * @param onError function that defines how to handle request failure
 */
fun fetchUsers(
    service: StackOverflowService,
    page: Int, pageSize: Int,
    onSuccess: (users: List<User>) -> Unit,
    onError: (error: String) -> Unit
) {
    Timber.i("get page: $page, usersPerPage: $pageSize")

    val response = service.getUsers(page = page, pageSize = pageSize)

    response.enqueue(object : Callback<UserListResponse> {
        override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
            Timber.i("fail to get data")
            onError(t.message ?: "unknown error")
        }

        override fun onResponse(
            call: Call<UserListResponse>,
            response: Response<UserListResponse>
        ) {
            if (response.isSuccessful) {
                val users = response.body()?.asModelObjects() ?: emptyList()
                onSuccess(users)
            } else {
                onError(response.errorBody()?.string() ?: "unknown error")
            }
        }

    })
}

/**
 * Fetch user's reputation
 * Trigger a request to the StackOverflow API with the following params:
 * @param userId user's id to get reputation
 * @param page request page index
 * @param pageSize number of reputation to be returned by the StackOverflow API per page
 *
 * The result of the request is handled by the implementation of the functions passed as params
 * @param onSuccess function that defines how to handle the reputation list received
 * @param onError function that defines how to handle request failure
 */
fun fetchReputation(
    service: StackOverflowService,
    userId: Long, page: Int, pageSize: Int,
    onSuccess: (reputationList: List<Reputation>) -> Unit,
    onError: (error: String) -> Unit
) {
    Timber.i("get reputation of user $userId, page: $page, reputationPerPage: $pageSize")

    val response = service.getReputation(
        userId = userId, page = page, pageSize = pageSize
    )

    response.enqueue(object : Callback<ReputationListResponse> {
        override fun onFailure(call: Call<ReputationListResponse>, t: Throwable) {
            Timber.i("fail to get data")
            onError(t.message ?: "unknown error")
        }

        override fun onResponse(
            call: Call<ReputationListResponse>,
            response: Response<ReputationListResponse>
        ) {
            if (response.isSuccessful) {
                val reputationList = response.body()?.asModelObjects() ?: emptyList()
                onSuccess(reputationList)
            } else {
                onError(response.errorBody()?.string() ?: "unknown error")
            }
        }

    })
}

/**
 * StackOverflow API communication setup via Retrofit.
 */
interface StackOverflowService {

    /**
     * Get users ordered by reputation.
     */
    @GET("users")
    fun getUsers(
        @Query("page") page: Int = 1,
        @Query("pagesize") pageSize: Int = 30,
        @Query("site") size: String = STACKOVERFLOW
    ): Call<UserListResponse>

    /**
     * Get user's reputation
     */
    @GET("users/{id}/reputation-history")
    fun getReputation(
        @Path("id") userId: Long,
        @Query("page") page: Int = 1,
        @Query("pagesize") pageSize: Int = 30,
        @Query("site") size: String = "stackoverflow"
    ): Call<ReputationListResponse>


    companion object {
        fun create(): StackOverflowService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(StackOverflowService::class.java)
        }
    }

}
