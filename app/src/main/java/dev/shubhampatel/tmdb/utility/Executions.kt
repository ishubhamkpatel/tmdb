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
            emit(Result.Failure("Api request failed. Reason: ${response.message()}"))
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

suspend fun <T> getProcessStateFlow(
    function: suspend () -> Flow<Result<T>>
): Flow<ProcessState<T>> {
    return flow {
        emit(ProcessState.Progress(true))
        function().collect { result ->
            when (result) {
                is Result.Failure -> {
                    emit(ProcessState.Error(result.message ?: "Something went wrong!"))
                }
                is Result.Success -> {
                    result.data?.let {
                        emit(ProcessState.Success(it))
                    } ?: kotlin.run {
                        emit(ProcessState.Error("Something went wrong!"))
                    }
                }
            }
        }
        emit(ProcessState.Progress(false))
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