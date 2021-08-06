package dev.shubhampatel.tmdb.ui.navigations

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import dev.shubhampatel.tmdb.ui.features.moviedetails.presentation.MovieDetailsScreen
import dev.shubhampatel.tmdb.ui.features.moviedetails.presentation.MovieDetailsViewModel
import dev.shubhampatel.tmdb.ui.features.movieslist.domain.MoviesListContract
import dev.shubhampatel.tmdb.ui.features.movieslist.presentation.MoviesListScreen
import dev.shubhampatel.tmdb.ui.features.movieslist.presentation.MoviesListViewModel
import dev.shubhampatel.tmdb.utility.AppConstants

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationKeys.Routes.MOVIES_LIST,
        builder = {
            composable(route = NavigationKeys.Routes.MOVIES_LIST) {
                MoviesListDestination(navController)
            }
            composable(
                route = NavigationKeys.Routes.MOVIE_DETAILS,
                arguments = listOf(navArgument(NavigationKeys.Args.MOVIE_ID) {
                    type = NavType.IntType
                    defaultValue = AppConstants.INVALID_INDEX
                    nullable = false
                })
            ) {
                MovieDetailsDestination()
            }
        })
}

@Composable
fun MoviesListDestination(navController: NavHostController) {
    val viewModel: MoviesListViewModel = hiltViewModel()
    MoviesListScreen(
        state = viewModel.currentState.value,
        effectFlow = viewModel.currentEffect,
        onEventSent = { event -> viewModel.onEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is MoviesListContract.Effect.Navigation.ToMovieDetails) {
                navController.navigate("${NavigationKeys.Routes.MOVIES_LIST}/${navigationEffect.movieId}")
            }
        }
    )
}

@Composable
fun MovieDetailsDestination() {
    val viewModel: MovieDetailsViewModel = hiltViewModel()
    MovieDetailsScreen(state = viewModel.currentState.value)
}

object NavigationKeys {

    object Routes {
        const val MOVIES_LIST = "movies_list"
        const val MOVIE_DETAILS = "$MOVIES_LIST/{${Args.MOVIE_ID}}"
    }

    object Args {
        const val MOVIE_ID = "movie_id"
    }
}