package dev.shubhampatel.tmdb.data.local

import dev.shubhampatel.tmdb.data.local.db.IDbManager
import dev.shubhampatel.tmdb.data.local.db.entity.MovieEntity
import dev.shubhampatel.tmdb.data.local.ds.IPrefsManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ILocalDataManager : IPrefsManager, IDbManager

class LocalDataManager @Inject constructor(
    private val prefsManager: IPrefsManager,
    private val dbManager: IDbManager
) : ILocalDataManager {

    override suspend fun clearPrefs() {
        prefsManager.clearPrefs()
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return prefsManager.isLoggedIn()
    }

    override suspend fun setLoggedIn(status: Boolean) {
        prefsManager.setLoggedIn(status)
    }

    override fun insertMovies(movies: List<MovieEntity>) {
        dbManager.insertMovies(movies)
    }

    override fun deleteAllMovies() {
        dbManager.deleteAllMovies()
    }

    override fun getMovieById(id: Int): Flow<MovieEntity> {
        return dbManager.getMovieById(id)
    }

    override fun getAllMovies(): Flow<List<MovieEntity>> {
        return dbManager.getAllMovies()
    }
}