package com.brown.stackuser.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.stackexchange.com/2.2/"
private const val STACKOVERFLOW = "stackoverflow"

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


    companion object {
        fun create(): StackOverflowService {
            var logger = HttpLoggingInterceptor()
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
