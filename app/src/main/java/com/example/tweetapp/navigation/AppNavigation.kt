package com.example.tweetapp.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tweetapp.screens.artistinfo.ArtistInfoScreen
import com.example.tweetapp.screens.mainscreen.MainScreen
import com.example.tweetapp.screens.movieInfo.MovieInfoScreen
import com.example.tweetapp.utils.NavigationScreen

@Composable
fun AppNavigationController() {
    val navController = rememberNavController()
    AppNavigation(navController)
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationScreen.MainScreen.route) {
        composable(route = NavigationScreen.MainScreen.route) {
            MainScreen(navController)
        }
        composable(route = NavigationScreen.MovieInfoScreen.route.plus("/{movieId}"), arguments = listOf(
            navArgument("movieId"){ type = NavType.IntType })) {
            val movieId = it.arguments?.getInt("movieId")
            MovieInfoScreen(navController,movieId)
        }
        composable(route = NavigationScreen.ArtistInfoScreen.route.plus("/{artistId}"), arguments = listOf(
            navArgument("artistId"){ type = NavType.IntType })) {
            val artistId = it.arguments?.getInt("artistId")
            ArtistInfoScreen(navController,artistId)
        }
    }
}



