package com.example.tweetapp.screens.searchscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPasteSearch
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.tweetapp.model.movies.MovieData
import com.example.tweetapp.model.movies.Result
import com.example.tweetapp.repository.DataState
import com.example.tweetapp.screens.generalscreens.CoilImage
import com.example.tweetapp.screens.generalscreens.SearchBar
import com.example.tweetapp.ui.component.SubtitlePrimary
import com.example.tweetapp.utils.GetGenreName
import com.example.tweetapp.utils.NavigationScreen
import com.example.tweetapp.utils.roundTo


@Composable
fun ShowSearchList(navController: NavHostController) {
    val searchedViewModel = hiltViewModel<SharedSearchViewModel>()
    val searchedMoviesList = searchedViewModel.searchedMovieList
    SearchBar({changedStr ->
        searchedViewModel.searchApi(changedStr)
    }){
        navController.popBackStack()
    }
    if(searchedMoviesList.value is DataState.Success<MovieData>){
        ShowMovieList(navController, (searchedMoviesList.value as DataState.Success<MovieData>).data.results)
    }
}

@Composable
fun ShowMovieList(
    navController: NavHostController,
    movies: List<Result>
) {
    if (movies.isEmpty()) {
        ShowEmptyList()
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(top = 60.dp)
                .background(Color.White)
        ) {
            items(movies) {
                Box() {
                    SearchedMovieItem(it, navController)
                }
            }
        }
    }
}

@Composable
fun ShowEmptyList() {
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 60.dp).fillMaxWidth()){
        Spacer(modifier = Modifier.size(180.dp))
        Icon(Icons.Filled.ContentPasteSearch, tint = Color.Blue,
            contentDescription = "Empty", modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = "Nothing To Show", color = Color.Black)
    }
}

@Composable
fun SearchedMovieItem(movie: Result, navController: NavHostController) {
    var genres = ""
    movie.genre_ids.forEach {
        genres += (GetGenreName.getGenres().get(it) + "   ")
    }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 6.dp, 2.dp, 6.dp)
            .clickable {
                navController.navigate(NavigationScreen.MovieInfoScreen.route.plus("/${movie.id}"))
            }) {
            CoilImage(navController = navController, item = movie, imageHeight = 150.dp, imageWidth = 120.dp)
            Column(modifier = Modifier.padding(vertical = 4.dp, horizontal = 25.dp)) {
                movie.title?.let {
                    Text(text = it, modifier = Modifier.padding(top = 10.dp), color = Color.Black, maxLines = 2,
                        fontSize = 16.sp, fontWeight = FontWeight.W700, overflow = TextOverflow.Ellipsis) }

                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, "Rating", Modifier.size(18.dp), tint = Color.Blue)
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    SubtitlePrimary(movie.vote_average.roundTo(1).toString())
                }
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Text(text = genres, fontSize = 13.sp, fontWeight = FontWeight.W500)
            }
        }
}
