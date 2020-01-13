package com.github.toprepo.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GetHubAPI {
    @GET("/r/{subreddit}/hot.json")
    fun getTop(
        @Query("q") limit: String,
        @Query("page") page: Int): Response<JSONObject>


    companion object {
        private const val BASE_URL = "https://api.github.com/search/repositories?sort=stars&order=desc"
        fun create(): GetHubAPI {

            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }


            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(GetHubAPI::class.java)
        }
    }
}