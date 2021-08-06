package dev.shubhampatel.tmdb.utility

sealed class Result<T>(val data: T? = null, val message: String? = null) {
    class Failure<T>(message: String, data: T? = null) : Result<T>(data, message)
    class Success<T>(data: T) : Result<T>(data)
}

sealed class ProcessState<out T> {
    data class Error(val message: String) : ProcessState<Nothing>()
    data class Progress(val isInProgress: Boolean) : ProcessState<Nothing>()
    data class Success<T>(val output: T?) : ProcessState<T>()
}