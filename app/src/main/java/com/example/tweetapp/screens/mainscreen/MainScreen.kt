package com.example.tweetapp.screens.mainscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tweetapp.model.moviegenre.Genre
import com.example.tweetapp.model.moviegenre.MovieGenres
import com.example.tweetapp.navigation.AppNavigation
import com.example.tweetapp.navigation.currentRoute
import com.example.tweetapp.repository.DataState
import com.example.tweetapp.screens.generalscreens.DrawerBody
import com.example.tweetapp.screens.generalscreens.HomeAppBar
import com.example.tweetapp.ui.component.ShowLoader
import com.example.tweetapp.utils.GetGenreName
import com.example.tweetapp.utils.NavigationScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val movieViewModel = hiltViewModel<MovieViewModel>()
    val scope = rememberCoroutineScope()
    val genreList = remember { mutableStateOf(arrayListOf<Genre>()) }


//    val loaderVisibleState = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit){
        movieViewModel.getMovieGenreList()
    }

    if(movieViewModel.genreList.value is DataState.Success<MovieGenres>){
        genreList.value = (movieViewModel.genreList.value as DataState.Success<MovieGenres>).data.genres
        if(genreList.value.isNotEmpty() && genreList.value[0].name != "All Videos"){
            genreList.value.add(0,Genre(-1,"All Videos"))
            GetGenreName.setGenre(genreList.value)
        }
    }
    ScaffoldFunc(navController,scaffoldState,scope,movieViewModel,genreList.value)

}

@Composable
fun ScaffoldFunc(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    movieViewModel: MovieViewModel,
    genreList: ArrayList<Genre>
) {
    val currentScreen = currentRoute(navController = navController)
    val genreId = remember { mutableStateOf(-1) }
    Scaffold(
        scaffoldState = scaffoldState, topBar = {
            when(currentScreen){
                NavigationScreen.MainScreen.route->{
                    HomeAppBar(title = (GetGenreName.getGenres().get(genreId.value) ?: "All Videos"),Icons.Filled.Menu) {
                        scope.launch {
                            scaffoldState.drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                }
                NavigationScreen.SearchScreen.route->{

                }
                else -> {
                    HomeAppBar(title = currentScreen?.substringBeforeLast("Screen").toString(),Icons.Filled.ArrowBack) {
                        scope.launch {
                            navController.popBackStack()
                        }
                    }
                }
                }
        },
        drawerContent = {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = "Movie Types", fontSize = 24.sp)
                Spacer(modifier = Modifier.size(16.dp))
                DrawerBody(genreList) {
                    genreId.value = it
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
            }
        },
        floatingActionButton = {
            if(currentScreen == NavigationScreen.MainScreen.route) {
                FloatingActionButton(onClick = {}, backgroundColor = Color.Blue.copy(red = 0.5f)) {
                    IconButton(onClick = { navController.navigate(NavigationScreen.SearchScreen.route) }) {
                        Icon(Icons.Filled.Search, tint = Color.White, contentDescription = "menu",)
                    }
                }
            }
        }
    ) {
        ShowLoader(movieViewModel.genreList.value)
        LaunchedEffect(key1 = genreId.value){
            movieViewModel.moviePagingList(genreId.value)
        }
        val movieList = movieViewModel.movieList.value?.collectAsLazyPagingItems()
        AppNavigation(navController, Modifier.padding(it), movieList)
    }
}


