package dev.shubhampatel.tmdb.data

import dev.shubhampatel.tmdb.data.local.ILocalDataManager
import dev.shubhampatel.tmdb.data.local.db.entity.MovieEntity
import dev.shubhampatel.tmdb.data.remote.IRemoteDataManager
import dev.shubhampatel.tmdb.models.MoviesListModel
import dev.shubhampatel.tmdb.utility.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IDataManager : ILocalDataManager, IRemoteDataManager {
    val localDataManager: ILocalDataManager
    val remoteDataManager: IRemoteDataManager
}

class DataManager @Inject constructor(
    override val localDataManager: ILocalDataManager,
    override val remoteDataManager: IRemoteDataManager
) : IDataManager {

    override suspend fun clearPrefs() {
        localDataManager.clearPrefs()
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return localDataManager.isLoggedIn()
    }

    override suspend fun setLoggedIn(status: Boolean) {
        localDataManager.setLoggedIn(status)
    }

    override fun insertMovies(movies: List<MovieEntity>) {
        localDataManager.insertMovies(movies)
    }

    override fun deleteAllMovies() {
        localDataManager.deleteAllMovies()
    }

    override fun getMovieById(id: Int): Flow<MovieEntity> {
        return localDataManager.getMovieById(id)
    }

    override fun getAllMovies(): Flow<List<MovieEntity>> {
        return localDataManager.getAllMovies()
    }

    override suspend fun getMoviesList(
        listId: Int, page: Int
    ): Flow<Result<MoviesListModel>> {
        return remoteDataManager.getMoviesList(listId, page)
    }
}