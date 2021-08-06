package dev.shubhampatel.tmdb.ui.features.moviedetails.domain

import dev.shubhampatel.tmdb.base.BaseContract
import dev.shubhampatel.tmdb.models.Movie

sealed interface MovieDetailsContract {
    sealed class Event : BaseContract.ViewEvent

    data class State(
        val movie: Movie? = null,
        val isLoading: Boolean = false
    ) : BaseContract.ViewState

    sealed class Effect : BaseContract.ViewSideEffect {
        object MovieDetailsLoaded : Effect()
        data class MovieDetailsLoadingError(val message: String) : Effect()
    }
}