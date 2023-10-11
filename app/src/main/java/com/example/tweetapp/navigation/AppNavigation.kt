package com.example.tweetapp.navigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.paging.compose.LazyPagingItems
import com.example.tweetapp.model.movies.Result
import com.example.tweetapp.screens.generalscreens.ShowPaginatedMovieList
import com.example.tweetapp.screens.artistinfo.ArtistInfoScreen
import com.example.tweetapp.screens.movieInfo.MovieInfoScreen
import com.example.tweetapp.screens.searchscreen.ShowSearchList
import com.example.tweetapp.utils.NavigationScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier,
    movieList: LazyPagingItems<Result>?
) {
    NavHost(navController = navController, startDestination = NavigationScreen.MainScreen.route, modifier = modifier) {
        composable(route = NavigationScreen.MainScreen.route) {
            movieList?.let {
                ShowPaginatedMovieList(navController, it)
            }
        }
        composable(route = NavigationScreen.MovieInfoScreen.route.plus("/{movieId}"),
            arguments = listOf(navArgument("movieId"){
                type = NavType.IntType
            })
        ) {
            val movieId = it.arguments?.getInt("movieId")
            MovieInfoScreen(navController,movieId)
        }
        composable(route = NavigationScreen.ArtistInfoScreen.route.plus("/{artistId}"), arguments = listOf(
            navArgument("artistId"){ type = NavType.IntType })) {
            val artistId = it.arguments?.getInt("artistId")
            ArtistInfoScreen(artistId)
        }

        composable(route = NavigationScreen.SearchScreen.route) {
            ShowSearchList(navController)
        }

    }
}


@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}

