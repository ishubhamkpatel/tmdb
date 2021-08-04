package dev.shubhampatel.tmdb.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.shubhampatel.tmdb.data.local.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM ${MovieEntity.TABLE_NAME}")
    fun deleteAllMovies()

    @Query("SELECT * FROM ${MovieEntity.TABLE_NAME} WHERE id = :id")
    fun getMovieById(id: Int): Flow<MovieEntity>

    @Query("SELECT * FROM ${MovieEntity.TABLE_NAME}")
    fun getAllMovies(): Flow<List<MovieEntity>>
}