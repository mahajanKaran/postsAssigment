package com.karan.mvvmassignment.network

import com.karan.mvvmassignment.data.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsApi {
    @GET("posts")
    suspend fun getPosts(
        @Query("_start") start: Int,
        @Query("_limit") limit: Int
    ): List<Post>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}