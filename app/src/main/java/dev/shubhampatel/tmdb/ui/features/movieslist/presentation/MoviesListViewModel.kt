package dev.shubhampatel.tmdb.ui.features.movieslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shubhampatel.tmdb.ui.features.movieslist.domain.MoviesListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val useCase: MoviesListUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            useCase.addSubscriptions()
        }
    }
}