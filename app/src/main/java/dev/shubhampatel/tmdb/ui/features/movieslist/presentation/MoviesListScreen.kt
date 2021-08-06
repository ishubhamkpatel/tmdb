package dev.shubhampatel.tmdb.ui.features.movieslist.presentation

import androidx.compose.runtime.Composable
import dev.shubhampatel.tmdb.ui.features.movieslist.domain.MoviesListContract
import kotlinx.coroutines.flow.Flow

@Composable
fun MoviesListScreen(
    state: MoviesListContract.State,
    effectFlow: Flow<MoviesListContract.Effect>?,
    onEventSent: (MoviesListContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: MoviesListContract.Effect.Navigation) -> Unit
) {

}