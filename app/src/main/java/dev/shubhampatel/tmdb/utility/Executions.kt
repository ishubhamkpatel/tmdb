package dev.shubhampatel.tmdb.utility

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import retrofit2.Response
import java.io.IOException

typealias ApiCall<DTO> = suspend () -> Response<DTO>

suspend fun <T> safeApiCall(
    errorCaseMessage: String = "Something went wrong!",
    noOfRetries: Int = 0,
    delayFactor: Long = 1000L,
    apiCall: ApiCall<T>
): Flow<ApiResponse<T>> {
    return flow<ApiResponse<T>> {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(ApiResponse.Success(it))
            } ?: kotlin.run {
                throw IOException("Empty response body!")
            }
        } else {
            emit(ApiResponse.Failure("Api request failed for reason: ${response.message()}"))
        }
    }.retryWhen { cause, attempt ->
        return@retryWhen if ((cause is IOException) and (attempt < noOfRetries)) {
            val delay = delayFactor.times(attempt.inc())
            emit(ApiResponse.Failure("Retrying in ${delay.div(1000L)} sec..."))
            delay(delay)
            true
        } else {
            emit(ApiResponse.Failure(errorCaseMessage))
            false
        }
    }.catch {
        emit(ApiResponse.Failure(errorCaseMessage))
    }.flowOn(Dispatchers.IO)
}

suspend fun <T> getViewStateFlow(
    function: suspend () -> ApiResponse<T>
): Flow<ViewState<T>> {
    return flow {
        emit(ViewState.Loading(true))
        when (val result = function()) {
            is ApiResponse.Success -> {
                result.data?.let {
                    emit(ViewState.RenderSuccess(it))
                } ?: kotlin.run {
                    emit(ViewState.RenderError("Something went wrong!"))
                }
            }
            is ApiResponse.Failure -> {
                emit(ViewState.RenderError(result.message ?: "Something went wrong!"))
            }
        }
        emit(ViewState.Loading(false))
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

fun <T> DataStore<Preferences>.getValue(key: Preferences.Key<T>, default: T): Flow<T> {
    return data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[key] ?: default
    }
}

suspend fun <T> DataStore<Preferences>.setValue(key: Preferences.Key<T>, value: T) {
    edit {
        it[key] = value
    }
}

suspend fun <T> DataStore<Preferences>.removeKey(key: Preferences.Key<T>) {
    edit {
        if (it.contains(key)) {
            it.remove(key)
        }
    }
}

suspend fun DataStore<Preferences>.clear() {
    edit {
        it.clear()
    }
}