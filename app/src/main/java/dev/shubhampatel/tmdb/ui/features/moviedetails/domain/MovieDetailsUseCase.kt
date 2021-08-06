package dev.shubhampatel.tmdb.ui.features.moviedetails.domain

import androidx.compose.runtime.State
import dev.shubhampatel.tmdb.base.BaseUseCase
import dev.shubhampatel.tmdb.ui.features.moviedetails.repository.MovieDetailsRepository
import dev.shubhampatel.tmdb.utility.ProcessState
import dev.shubhampatel.tmdb.utility.getProcessStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

interface MovieDetailsUseCase {
    val repository: MovieDetailsRepository
    val currentState: State<MovieDetailsContract.State>
    suspend fun addSubscriptions()
    suspend fun fetchMovieDetails(movieId: Int)
}

class MovieDetailsUseCaseImpl @Inject constructor(
    override val repository: MovieDetailsRepository
) : BaseUseCase<MovieDetailsContract.Event, MovieDetailsContract.State, MovieDetailsContract.Effect>(),
    MovieDetailsUseCase {

    override val currentState: State<MovieDetailsContract.State>
        get() = super.viewState

    override fun setInitialState(): MovieDetailsContract.State {
        return MovieDetailsContract.State(isLoading = true)
    }

    override suspend fun handleEvents(event: MovieDetailsContract.Event) {
        // no events
    }

    override suspend fun addSubscriptions() {
        super.subscribeToEvents()
    }

    override suspend fun fetchMovieDetails(movieId: Int) {
        getProcessStateFlow {
            repository.fetchMovieDetails(movieId)
        }.collect {
            when (it) {
                is ProcessState.Error -> {
                    setEffect { MovieDetailsContract.Effect.MovieDetailsLoadingError(it.message) }
                }
                is ProcessState.Progress -> this.setInitialState()
                is ProcessState.Success -> {
                    val movie = it.output
                    movie?.let {
                        setState { copy(movie = it, isLoading = false) }
                        setEffect { MovieDetailsContract.Effect.MovieDetailsLoaded }
                    } ?: kotlin.run {
                        setEffect { MovieDetailsContract.Effect.MovieDetailsLoadingError("Retry") }
                    }
                }
            }
        }
    }
}