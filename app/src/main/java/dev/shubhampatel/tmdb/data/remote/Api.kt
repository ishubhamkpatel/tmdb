package dev.shubhampatel.tmdb.data.remote

import dev.shubhampatel.tmdb.models.MoviesListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    companion object {
        private const val LIST = "/list"
    }

    @GET(LIST)
    suspend fun getMoviesList(
        @Path("list_id") listId: Int,
        @Query("page") page: Int
    ): Response<MoviesListModel>
}