package dev.shubhampatel.tmdb.data.remote

import dev.shubhampatel.tmdb.models.MoviesListModel
import dev.shubhampatel.tmdb.utility.Result
import dev.shubhampatel.tmdb.utility.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IRemoteDataManager {
    suspend fun getMoviesList(listId: Int, page: Int): Flow<Result<MoviesListModel>>
}

class RemoteDataManager @Inject constructor(private val api: Api) : IRemoteDataManager {

    override suspend fun getMoviesList(
        listId: Int, page: Int
    ): Flow<Result<MoviesListModel>> {
        return safeApiCall("Error fetching movies list.") {
            api.getMoviesList(listId, page)
        }
    }
}