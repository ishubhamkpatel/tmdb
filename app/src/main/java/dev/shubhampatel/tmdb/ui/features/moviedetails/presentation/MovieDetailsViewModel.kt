package dev.shubhampatel.tmdb.ui.features.moviedetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shubhampatel.tmdb.ui.features.moviedetails.domain.MovieDetailsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val useCase: MovieDetailsUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            useCase.addSubscriptions()
        }
    }
}