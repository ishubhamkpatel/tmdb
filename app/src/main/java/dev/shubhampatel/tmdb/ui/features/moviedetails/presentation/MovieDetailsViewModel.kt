package dev.shubhampatel.tmdb.ui.features.moviedetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shubhampatel.tmdb.ui.features.moviedetails.domain.MovieDetailsUseCase
import dev.shubhampatel.tmdb.ui.navigations.NavigationKeys
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: MovieDetailsUseCase
) : ViewModel() {

    val currentState = useCase.currentState

    init {
        addSubscriptions()
        val movieId = savedStateHandle.get<Int>(NavigationKeys.Args.MOVIE_ID)
            ?: throw IllegalStateException("No movieId was passed to destination.")
        fetchMovieDetails(movieId)
    }

    private fun addSubscriptions() {
        viewModelScope.launch {
            useCase.addSubscriptions()
        }
    }

    private fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            useCase.fetchMovieDetails(movieId)
        }
    }
}