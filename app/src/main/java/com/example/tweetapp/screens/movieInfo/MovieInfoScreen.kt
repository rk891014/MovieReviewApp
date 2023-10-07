package com.example.tweetapp.screens.movieInfo

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.tweetapp.model.artist.Artist
import com.example.tweetapp.model.artist.Cast
import com.example.tweetapp.model.movies.MovieData
import com.example.tweetapp.model.movies.Result
import com.example.tweetapp.repository.DataState
import com.example.tweetapp.screens.mainscreen.MovieItemView
import com.example.tweetapp.ui.component.SubtitlePrimary
import com.example.tweetapp.ui.component.SubtitleSecondary
import com.example.tweetapp.utils.AppConstant
import com.example.tweetapp.utils.ExpandingText
import com.example.tweetapp.utils.NavigationScreen
import com.example.tweetapp.utils.hourMinutes
import com.example.tweetapp.utils.roundTo
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun MovieInfoScreen(navController: NavHostController, movieId: Int?) {

    val movieInfoViewModel = hiltViewModel<MovieInfoViewModel>()
    val loaderVisibleState = remember { mutableStateOf(true) }
//    ShowLoader(isLoaderVisible = loaderVisibleState)

    val movieDetail = movieInfoViewModel.movieDetail
    val recommendedMovie = movieInfoViewModel.recommendedMovie
    val artist = movieInfoViewModel.artist

    LaunchedEffect(key1 = Unit){
        movieId?.let {
            movieInfoViewModel.movieData(it)
            movieInfoViewModel.recommendedMovies(it)
            movieInfoViewModel.movieCredit(it)
        }
    }

    ShowPageData(navController,movieDetail,recommendedMovie,artist)

}

@Composable
fun ShowPageData(
    navController: NavHostController,
    movieDetail: MutableState<DataState<Result>?>,
    recommendedMovie: MutableState<DataState<MovieData>?>,
    artist: MutableState<DataState<Artist>?>
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        movieDetail.value?.let {
            ShowMovieInfoData(it)
        }
        recommendedMovie.value?.let {
            ShowRecommendedMovies(navController,it)
        }
        artist.value?.let { 
            ShowArtistList(navController,it)
        }
    }
}

@Composable
fun ShowArtistList(navController: NavHostController, artistList: DataState<Artist>) {
    if(artistList is DataState.Success<Artist> && artistList.data.cast.isNotEmpty()){
        Column {
            Text(text = "Cast", color = Color.Black, fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(10.dp))
            LazyRow(modifier = Modifier.padding(5.dp,0.dp,0.dp,0.dp),content = {
                items(artistList.data.cast){
                    ArtistItemView(navController,it)
                }
            })
        }
    }
}

@Composable
fun ArtistItemView(navController: NavHostController, artist: Cast) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp).clickable {
            navController.navigate(NavigationScreen.ArtistInfoScreen.route.plus("/${artist.id}"))
        }) {
        CoilImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(50)),
            imageModel = { AppConstant.IMAGE_URL.plus(artist.profile_path) },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                contentDescription = "Movie detail",
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
        Text(text = artist.name, fontSize = 16.sp, color = Color.Gray,
            modifier = Modifier.size(110.dp,40.dp), overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center, maxLines = 1)
    }
}

@Composable
fun ShowRecommendedMovies(navController: NavHostController, movies: DataState<MovieData>) {
    if(movies is DataState.Success<MovieData> && movies.data.results.isNotEmpty()){
        Column {
            Text(text = "Similar", color = Color.Black, fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(10.dp))
            LazyRow(modifier = Modifier.padding(5.dp,0.dp,0.dp,0.dp),content = {
                items(movies.data.results){
                    MovieItemView(navController,it,220.dp,150.dp)
                }
            })
        }
    }
}

@Composable
fun ShowMovieInfoData(movie: DataState<Result>) {
    if (movie is DataState.Success<Result>) {
        Column {
            CoilImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                imageModel = { AppConstant.IMAGE_URL.plus(movie.data.poster_path) },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    contentDescription = "Movie detail",
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                movie.data.title?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(top = 10.dp),
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W700,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp, top = 10.dp)
                ) {

                    Column(Modifier.weight(1f)) {
                        SubtitlePrimary(movie.data.original_language)
                        SubtitleSecondary("Language")
                    }
                    Column(Modifier.weight(1f)) {
                        SubtitlePrimary(movie.data.vote_average.roundTo(1).toString())
                        SubtitleSecondary("Rating")
                    }
                    Column(Modifier.weight(1f)) {
                        SubtitlePrimary(movie.data.runtime.hourMinutes())
                        SubtitleSecondary("Duration")
                    }
                    Column(Modifier.weight(1f)) {
                        SubtitlePrimary( movie.data.release_date)
                        SubtitleSecondary("Release Date")
                    }
                }
                Text(
                    text = "Description",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                movie.data.overview?.let { ExpandingText(text = it) }

            }
        }
    }
}
