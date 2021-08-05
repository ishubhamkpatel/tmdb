package dev.shubhampatel.tmdb.utility

sealed class Result<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Result<T>(data)
    class Failure<T>(message: String, data: T? = null) : Result<T>(data, message)
}

sealed class State<out T> {
    data class Loading(val isLoading: Boolean) : State<Nothing>()
    data class Error(val message: String) : State<Nothing>()
    data class Success<T>(val output: T) : State<T>()
}