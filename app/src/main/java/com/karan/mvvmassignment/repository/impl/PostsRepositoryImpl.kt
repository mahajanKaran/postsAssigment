package com.karan.mvvmassignment.repository.impl

import com.karan.mvvmassignment.repository.PostsRepository
import com.karan.mvvmassignment.utils.NetworkConnectivity
import com.karan.mvvmassignment.data.Post
import com.karan.mvvmassignment.network.PostsApi
import com.karan.mvvmassignment.repository.NetworkResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@Singleton
class PostsRepositoryImpl @Inject constructor(
    private val api: PostsApi,
    private val networkConnectivity: NetworkConnectivity
) : PostsRepository {

    override suspend fun getPosts(page: Int, pageSize: Int): Flow<NetworkResponse<List<Post>>> =
        flow {
            emit(NetworkResponse.Loading())
            if (!networkConnectivity.isConnected()) {
                emit(NetworkResponse.error("You are offline. Connect to the Internet to access the app"))
                return@flow
            } else {
                emit(NetworkResponse.success(api.getPosts((page - 1) * pageSize, pageSize)))
            }
        }.catch { e -> emit(NetworkResponse.error("Something went wrong $e")) }

}