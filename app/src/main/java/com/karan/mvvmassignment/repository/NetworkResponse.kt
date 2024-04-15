package com.karan.mvvmassignment.repository


sealed class NetworkResponse<T> {
    data class Success<T>(val data: T) : NetworkResponse<T>()
    data class Error<T>(val message: String) : NetworkResponse<T>()
    class Loading<T> : NetworkResponse<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> error(message: String) = Error<T>(message)
    }
}
