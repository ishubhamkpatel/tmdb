package dev.shubhampatel.tmdb.ui.features.movieslist.domain

import dev.shubhampatel.tmdb.base.BaseUseCase
import dev.shubhampatel.tmdb.ui.features.movieslist.repository.MoviesListRepository
import dev.shubhampatel.tmdb.utility.State
import dev.shubhampatel.tmdb.utility.getStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

interface MoviesListUseCase {
    val repository: MoviesListRepository
    suspend fun addSubscriptions()
    suspend fun fetchMoviesList()
}

class MoviesListUseCaseImpl @Inject constructor(
    override val repository: MoviesListRepository
) : BaseUseCase<MoviesListContract.Event, MoviesListContract.State, MoviesListContract.Effect>(),
    MoviesListUseCase {

    override fun setInitialState(): MoviesListContract.State {
        return MoviesListContract.State(isLoading = true)
    }

    override suspend fun handleEvents(event: MoviesListContract.Event) {
        when (event) {
            is MoviesListContract.Event.MovieSelection -> {
                setEffect { MoviesListContract.Effect.Navigation.ToMovieDetails(event.movieId) }
            }
        }
    }

    override suspend fun addSubscriptions() {
        super.subscribeToEvents()
    }

    override suspend fun fetchMoviesList() {
        getStateFlow {
            repository.fetchMoviesList(0, 0)
        }.collect {
            when (it) {
                is State.Loading -> this.setInitialState()
                is State.Success -> {
                    val movies = it.output.movies?.filterNotNull()
                    if (!movies.isNullOrEmpty()) {
                        setState { copy(movies = movies, isLoading = false) }
                        setEffect { MoviesListContract.Effect.MoviesLoaded }
                    } else {
                        setEffect { MoviesListContract.Effect.MoviesLoadingError("Retry!") }
                    }
                }
                is State.Error -> {
                    setEffect { MoviesListContract.Effect.MoviesLoadingError(it.message) }
                }
            }
        }
    }
}