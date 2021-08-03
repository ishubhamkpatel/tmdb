package dev.shubhampatel.tmdb.utility

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import retrofit2.Response
import java.io.IOException

typealias ApiCall<DTO> = suspend () -> Response<DTO>

suspend fun <T> safeApiCall(
    apiCall: ApiCall<T>,
    errorCaseMessage: String = "Something went wrong!",
    noOfRetries: Int = 0,
    delayFactor: Long = 1000L
): Flow<Result<T>> {
    return flow<Result<T>> {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(Result.Success(it))
            } ?: kotlin.run {
                throw IOException("Empty response body!")
            }
        } else {
            emit(Result.Failure("Api request failed for reason: ${response.message()}"))
        }
    }.retryWhen { cause, attempt ->
        return@retryWhen if ((cause is IOException) and (attempt < noOfRetries)) {
            val delay = delayFactor.times(attempt.inc())
            emit(Result.Failure("Retrying in ${delay.div(1000L)} sec..."))
            delay(delay)
            true
        } else {
            emit(Result.Failure(errorCaseMessage))
            false
        }
    }.catch {
        emit(Result.Failure(errorCaseMessage))
    }.flowOn(Dispatchers.IO)
}

suspend fun <T> getViewStateFlow(
    function: suspend () -> Result<T>
): Flow<ViewState<T>> {
    return flow {
        emit(ViewState.Loading(true))
        when (val result = function()) {
            is Result.Success -> {
                result.data?.let {
                    emit(ViewState.RenderSuccess(it))
                } ?: kotlin.run {
                    emit(ViewState.RenderError("Something went wrong!"))
                }
            }
            is Result.Failure -> {
                emit(ViewState.RenderError(result.message ?: "Something went wrong!"))
            }
        }
        emit(ViewState.Loading(false))
    }
}