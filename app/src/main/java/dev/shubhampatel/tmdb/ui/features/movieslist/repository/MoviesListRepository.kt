package dev.shubhampatel.tmdb.ui.features.movieslist.repository

import dev.shubhampatel.tmdb.data.IDataManager
import dev.shubhampatel.tmdb.models.MoviesListModel
import dev.shubhampatel.tmdb.utility.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MoviesListRepository {
    val dataManager: IDataManager
    suspend fun fetchMoviesList(listId: Int, page: Int): Flow<Result<MoviesListModel>>
}

class MoviesListRepositoryImpl @Inject constructor(
    override val dataManager: IDataManager
) : MoviesListRepository {

    override suspend fun fetchMoviesList(listId: Int, page: Int): Flow<Result<MoviesListModel>> {
        return dataManager.getMoviesList(listId, page)
    }
}