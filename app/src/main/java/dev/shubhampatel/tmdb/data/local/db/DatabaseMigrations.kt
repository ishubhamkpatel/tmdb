package dev.shubhampatel.tmdb.data.local.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.shubhampatel.tmdb.data.local.db.entity.MovieEntity

object DatabaseMigrations {

    const val DB_VERSION = 1
    val MIGRATIONS = arrayOf(migration01())

    private fun migration01(): Migration = object : Migration(0, 1) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE ${MovieEntity.TABLE_NAME} ADD COLUMN body TEXT")
        }
    }
}