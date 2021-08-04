package dev.shubhampatel.tmdb.data.local.db

import dev.shubhampatel.tmdb.data.local.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IDbManager {
    fun insertMovies(movies: List<MovieEntity>)
    fun deleteAllMovies()
    fun getMovieById(id: Int): Flow<MovieEntity>
    fun getAllMovies(): Flow<List<MovieEntity>>
}

class DbManager @Inject constructor(private val db: AppDatabase) : IDbManager {

    override fun insertMovies(movies: List<MovieEntity>) {
        db.getMoviesDao().insertMovies(movies)
    }

    override fun deleteAllMovies() {
        db.getMoviesDao().deleteAllMovies()
    }

    override fun getMovieById(id: Int): Flow<MovieEntity> {
        return db.getMoviesDao().getMovieById(id)
    }

    override fun getAllMovies(): Flow<List<MovieEntity>> {
        return db.getMoviesDao().getAllMovies()
    }
}