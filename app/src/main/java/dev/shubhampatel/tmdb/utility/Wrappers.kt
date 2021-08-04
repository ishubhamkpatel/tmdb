package dev.shubhampatel.tmdb.utility

sealed class ApiResponse<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ApiResponse<T>(data)
    class Failure<T>(message: String, data: T? = null) : ApiResponse<T>(data, message)
}

sealed class ViewState<out T> {
    object Initial : ViewState<Nothing>()
    data class Loading(val isLoading: Boolean) : ViewState<Nothing>()
    data class RenderError(val message: String) : ViewState<Nothing>()
    data class RenderSuccess<T>(val output: T) : ViewState<T>()
}