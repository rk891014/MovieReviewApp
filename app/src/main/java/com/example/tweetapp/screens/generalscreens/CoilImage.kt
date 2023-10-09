package com.example.tweetapp.screens.generalscreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.example.tweetapp.R
import com.example.tweetapp.model.movies.Result
import com.example.tweetapp.utils.AppConstant
import com.example.tweetapp.utils.NavigationScreen
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun CoilImage(navController: NavHostController, item: Result, imageHeight: Dp, imageWidth: Dp) {
    CoilImage(
        modifier = Modifier
            .size(imageWidth, imageHeight)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                navController.navigate(NavigationScreen.MovieInfoScreen.route.plus("/${item.id}"))
            },
        failure = {
            Column(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .paint(
                        painter = painterResource(id = R.drawable.defaultbackground),
                        contentScale = ContentScale.Crop
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item.title?.let { it1 ->
                    ShowFailureImage(item.original_language, it1)
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