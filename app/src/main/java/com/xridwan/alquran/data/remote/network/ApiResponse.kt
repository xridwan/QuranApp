package com.xridwan.alquran.data.remote.network

sealed class ApiResponse<out R> {
    object Empty : ApiResponse<Nothing>()
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
}