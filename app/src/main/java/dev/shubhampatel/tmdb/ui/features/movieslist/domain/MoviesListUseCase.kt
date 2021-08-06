package dev.shubhampatel.tmdb.ui.features.movieslist.domain

import androidx.compose.runtime.State
import dev.shubhampatel.tmdb.base.BaseUseCase
import dev.shubhampatel.tmdb.ui.features.movieslist.repository.MoviesListRepository
import dev.shubhampatel.tmdb.utility.ProcessState
import dev.shubhampatel.tmdb.utility.getProcessStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

interface MoviesListUseCase {
    val repository: MoviesListRepository
    val currentState: State<MoviesListContract.State>
    val currentEffect: Flow<MoviesListContract.Effect>
    suspend fun onEvent(event: MoviesListContract.Event)
    suspend fun addSubscriptions()
    suspend fun fetchMoviesList()
}

class MoviesListUseCaseImpl @Inject constructor(
    override val repository: MoviesListRepository
) : BaseUseCase<MoviesListContract.Event, MoviesListContract.State, MoviesListContract.Effect>(),
    MoviesListUseCase {

    override val currentState: State<MoviesListContract.State>
        get() = super.viewState

    override val currentEffect: Flow<MoviesListContract.Effect>
        get() = super.effect

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

    override suspend fun onEvent(event: MoviesListContract.Event) {
        super.setEvent(event)
    }

    override suspend fun addSubscriptions() {
        super.subscribeToEvents()
    }

    override suspend fun fetchMoviesList() {
        getProcessStateFlow {
            repository.fetchMoviesList(0, 0)
        }.collect {
            when (it) {
                is ProcessState.Error -> {
                    setEffect { MoviesListContract.Effect.MoviesLoadingError(it.message) }
                }
                is ProcessState.Progress -> this.setInitialState()
                is ProcessState.Success -> {
                    val movies = it.output?.movies?.filterNotNull()
                    if (!movies.isNullOrEmpty()) {
                        setState { copy(movies = movies, isLoading = false) }
                        setEffect { MoviesListContract.Effect.MoviesLoaded }
                    } else {
                        setEffect { MoviesListContract.Effect.MoviesLoadingError("Retry!") }
                    }
                }
            }
        }
    }
}