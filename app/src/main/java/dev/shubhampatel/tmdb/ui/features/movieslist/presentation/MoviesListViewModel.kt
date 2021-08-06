package dev.shubhampatel.tmdb.ui.features.movieslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shubhampatel.tmdb.ui.features.movieslist.domain.MoviesListContract
import dev.shubhampatel.tmdb.ui.features.movieslist.domain.MoviesListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val useCase: MoviesListUseCase
) : ViewModel() {

    val currentState = useCase.currentState
    val currentEffect = useCase.currentEffect

    init {
        addSubscriptions()
        fetchMoviesList()
    }

    private fun addSubscriptions() {
        viewModelScope.launch {
            useCase.addSubscriptions()
        }
    }

    private fun fetchMoviesList() {
        viewModelScope.launch {
            useCase.fetchMoviesList()
        }
    }

    fun onEvent(event: MoviesListContract.Event) {
        viewModelScope.launch {
            useCase.onEvent(event)
        }
    }
}