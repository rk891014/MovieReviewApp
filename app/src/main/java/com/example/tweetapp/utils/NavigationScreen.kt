package com.example.tweetapp.utils

sealed class NavigationScreen(val route : String){

    object MainScreen : NavigationScreen("MainScreen")

    object MovieInfoScreen : NavigationScreen("MovieInfoScreen")

    object ArtistInfoScreen : NavigationScreen("ArtistInfoScreen")
}
