package com.example.tweetapp.screens.mainscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tweetapp.R
import com.example.tweetapp.model.movies.MovieData
import com.example.tweetapp.model.movies.Result
import com.example.tweetapp.ui.component.ShowLoader
import com.example.tweetapp.utils.AppConstant
import com.example.tweetapp.utils.NavigationScreen
import com.example.tweetapp.utils.pagingItems
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun MainScreen(navController: NavHostController) {

    val movieViewModel = hiltViewModel<MovieViewModel>()
//    val movieList = remember { mutableStateOf(MovieDetail()) }
    val loaderVisibleState = remember { mutableStateOf(true) }
    ShowLoader(isLoaderVisible = loaderVisibleState)

//    if (movieViewModel.movieData.value is DataState.Success<MovieDetail>) {
//        movieList.value = (movieViewModel.movieData.value as DataState.Success<MovieDetail>).data
//        ShowMovieList(navController,movieList.value)
//        loaderVisibleState.value = false
//    }
    val movieList = movieViewModel.moviePagingList().collectAsLazyPagingItems()
    ShowPaginatedMovieList(navController, movieList, loaderVisibleState)
}

@Composable
fun ShowPaginatedMovieList(
    navController: NavHostController,
    movieList: LazyPagingItems<Result>,
    loaderVisibleState: MutableState<Boolean>
) {

    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.SpaceAround){
        pagingItems(movieList){item->
                item?.let {
                    MovieItemView(navController,it,250.dp,250.dp)
                    loaderVisibleState.value = false
                }
            }
    }

}

@Composable
fun ShowMovieList(navController: NavHostController, movieDetail: MovieData) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.background(Color.White)){

        movieDetail.results?.let {movieList->
            items(movieList){
//                MovieItemView(navController, it, 250.dp,250.dp)
            }
        }
    }
}

@Composable
fun MovieItemView(navController: NavHostController, item: Result, imageHeight: Dp, imageWidth: Dp) {
    Column(modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 0.dp, bottom = 10.dp)) {
        CoilImage(
            modifier = Modifier
                .size(imageWidth, imageHeight)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    navController.navigate(NavigationScreen.MovieInfoScreen.route.plus("/${item.id}"))
                },
            failure = {
                        Column(modifier = Modifier
                            .fillMaxSize(1f)
                            .paint(
                                painter = painterResource(id = R.drawable.defaultbackground),
                                contentScale = ContentScale.Crop
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center) {
                            item.title?.let { it1 -> ShowFailureImage(item.overview.toString(), it1)
                            }
                        }
                      },
            imageModel = { AppConstant.IMAGE_URL.plus(item.poster_path) },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                contentDescription = "Movie item",
                colorFilter = null,
            ),
            component = rememberImageComponent {
                +CircularRevealPlugin(
                    duration = 800
                )
                +ShimmerPlugin(
                    baseColor = Color.Gray,
                    highlightColor = Color.Cyan
                )
            },
        )
    }
}

@Composable
fun ShowFailureImage(movieName: String = "movie", typeName: String= "") {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Text(text = movieName, fontSize = 25.sp, color = Color.White,
            fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
        Text(text = typeName, fontSize = 15.sp, color = Color.White
            , textAlign = TextAlign.Center)
    }
}
