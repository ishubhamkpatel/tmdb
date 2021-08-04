package dev.shubhampatel.tmdb.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = MovieEntity.TABLE_NAME)
data class MovieEntity(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "title") val title : String?,
    @ColumnInfo(name = "media_type") val mediaType : String?,
    @ColumnInfo(name = "release_date") val releaseDate : String?,
    @ColumnInfo(name = "overview") val overview : String?,
    @ColumnInfo(name = "original_title") val originalTitle : String?,
    @ColumnInfo(name = "original_language") val originalLanguage : String?,
    @ColumnInfo(name = "vote_average") val voteAverage : Double?,
    @ColumnInfo(name = "vote_count") val voteCount : Int?,
    @ColumnInfo(name = "popularity") val popularity : Double?
) {
    companion object {
        const val TABLE_NAME = "movies"
    }
}