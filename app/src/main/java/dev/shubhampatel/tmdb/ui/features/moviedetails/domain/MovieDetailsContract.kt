package dev.shubhampatel.tmdb.ui.features.moviedetails.domain

import dev.shubhampatel.tmdb.base.BaseContract

sealed interface MovieDetailsContract {
    sealed class Event : BaseContract.ViewEvent

    data class State(val isLoading: Boolean = false) : BaseContract.ViewState

    sealed class Effect : BaseContract.ViewSideEffect
}