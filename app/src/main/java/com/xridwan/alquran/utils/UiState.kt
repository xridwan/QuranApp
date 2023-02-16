package com.xridwan.alquran.utils

data class UiState<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): UiState<T> =
            UiState(Status.SUCCESS, data, null)

        fun <T> loading(data: T?): UiState<T> =
            UiState(Status.LOADING, data, null)

        fun <T> error(data: T?, message: String?): UiState<T> =
            UiState(Status.ERROR, data, message)
    }
}

enum class Status {
    SUCCESS,
    LOADING,
    ERROR,
}