package dev.shubhampatel.tmdb.ui.features.movieslist.domain

import dev.shubhampatel.tmdb.base.BaseContract
import dev.shubhampatel.tmdb.models.Movie

sealed interface MoviesListContract {
    sealed class Event : BaseContract.ViewEvent {
        data class MovieSelection(val movieId: Int) : Event()
    }

    data class State(
        val movies: List<Movie> = listOf(),
        val isLoading: Boolean = false
    ) : BaseContract.ViewState

    sealed class Effect : BaseContract.ViewSideEffect {
        object MoviesLoaded : Effect()
        data class MoviesLoadingError(val message: String) : Effect()
        sealed class Navigation : Effect() {
            data class ToMovieDetails(val movieId: Int) : Navigation()
        }
    }
}