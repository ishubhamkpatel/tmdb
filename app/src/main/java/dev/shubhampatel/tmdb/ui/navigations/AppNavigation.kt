package dev.shubhampatel.tmdb.ui.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController

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
                    type = NavType.StringType
                })
            ) {
                MovieDetailsDestination()
            }
        })
}

@Composable
fun MoviesListDestination(navController: NavHostController) {

}

@Composable
fun MovieDetailsDestination() {

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