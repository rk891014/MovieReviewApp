package com.example.tweetapp.screens.generalscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.example.tweetapp.model.movies.MovieData
import com.example.tweetapp.model.movies.Result
import com.example.tweetapp.utils.pagingItems

@Composable
fun ShowPaginatedMovieList(
    navController: NavHostController,
    movieList: LazyPagingItems<Result>
) {



    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.SpaceAround){
        pagingItems(movieList){item->
            item?.let {
                MovieItemView(navController,it,250.dp,250.dp)
//                    loaderVisibleState.value = false
            }
        }
    }

}

@Composable
fun MovieItemView(navController: NavHostController, item: Result, imageHeight: Dp, imageWidth: Dp) {
    Column(modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 0.dp, bottom = 10.dp)) {
        CoilImage(navController = navController, item = item, imageHeight = imageHeight, imageWidth = imageWidth)
    }
}
