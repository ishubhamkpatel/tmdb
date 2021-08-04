package dev.shubhampatel.tmdb.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.shubhampatel.tmdb.data.local.db.dao.MoviesDao
import dev.shubhampatel.tmdb.data.local.db.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = DatabaseMigrations.DB_VERSION,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "tmdb_database"
    }

    abstract fun getMoviesDao(): MoviesDao
}