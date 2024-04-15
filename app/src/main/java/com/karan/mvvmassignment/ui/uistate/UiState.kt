package com.karan.mvvmassignment.ui.uistate

import com.karan.mvvmassignment.data.Post

sealed class UiState {
    object Loading : UiState()
    data class Success(val posts: List<Post>) : UiState()
    data class Error(val message: String) : UiState()
}