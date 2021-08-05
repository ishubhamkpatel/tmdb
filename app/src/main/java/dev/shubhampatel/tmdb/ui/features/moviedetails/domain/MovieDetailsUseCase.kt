package dev.shubhampatel.tmdb.ui.features.moviedetails.domain

import dev.shubhampatel.tmdb.base.BaseUseCase
import dev.shubhampatel.tmdb.ui.features.moviedetails.repository.MovieDetailsRepository
import javax.inject.Inject

interface MovieDetailsUseCase {
    val repository: MovieDetailsRepository
    suspend fun addSubscriptions()
}

class MovieDetailsUseCaseImpl @Inject constructor(
    override val repository: MovieDetailsRepository
) : BaseUseCase<MovieDetailsContract.Event, MovieDetailsContract.State, MovieDetailsContract.Effect>(),
    MovieDetailsUseCase {

    override fun setInitialState(): MovieDetailsContract.State {
        return MovieDetailsContract.State(isLoading = true)
    }

    override suspend fun handleEvents(event: MovieDetailsContract.Event) {
        // no events
    }

    override suspend fun addSubscriptions() {
        super.subscribeToEvents()
    }
}