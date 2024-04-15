package com.karan.mvvmassignment.repository

/**
 *
 */

import com.karan.mvvmassignment.data.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface PostsRepository {
    suspend fun getPosts(page: Int, pageSize: Int): Flow<NetworkResponse<List<Post>>>
}
