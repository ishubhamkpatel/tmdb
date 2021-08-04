package dev.shubhampatel.tmdb.data.remote

import dev.shubhampatel.tmdb.models.MoviesListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    private object Versions {
        const val v3 = "/3"
        const val v4 = "/4"
    }

    private object EndPoints {
        const val LIST = "/list"
    }

    @GET(Versions.v4.plus(EndPoints.LIST))
    suspend fun getMoviesList(
        @Path("list_id") listId: Int,
        @Query("page") page: Int
    ): Response<MoviesListModel>
}