package dev.shubhampatel.tmdb.ui.features.moviedetails.repository

import dev.shubhampatel.tmdb.data.IDataManager
import dev.shubhampatel.tmdb.models.Movie
import dev.shubhampatel.tmdb.utility.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MovieDetailsRepository {
    val dataManager: IDataManager
    suspend fun fetchMovieDetails(movieId: Int): Flow<Result<Movie>>
}

class MovieDetailsRepositoryImpl @Inject constructor(
    override val dataManager: IDataManager
) : MovieDetailsRepository {

    override suspend fun fetchMovieDetails(movieId: Int): Flow<Result<Movie>> {
        return dataManager.getMovieDetails(movieId)
    }
}