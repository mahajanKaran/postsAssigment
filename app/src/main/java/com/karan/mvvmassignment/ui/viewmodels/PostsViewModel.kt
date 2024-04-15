package com.karan.mvvmassignment.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karan.mvvmassignment.repository.PostsRepository
import com.karan.mvvmassignment.data.Post
import com.karan.mvvmassignment.repository.NetworkResponse
import com.karan.mvvmassignment.ui.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repository: PostsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private var currentPage = 1
    private val pageSize = 20

    private var posts = mutableListOf<Post>()

    fun fetchPosts() {
        viewModelScope.launch {
            repository.getPosts(currentPage, pageSize)
                .distinctUntilChanged().collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                            _uiState.value = UiState.Loading
                        }

                        is NetworkResponse.Success -> {
                            posts.addAll(result.data)
                            _uiState.value = UiState.Success(posts)
                            currentPage++
                        }

                        is NetworkResponse.Error -> _uiState.value =
                            UiState.Error(result.message)
                    }
                }
        }
    }
}