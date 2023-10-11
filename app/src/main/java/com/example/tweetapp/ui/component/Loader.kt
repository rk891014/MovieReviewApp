package com.example.tweetapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.example.tweetapp.repository.DataState

@Composable
fun ShowLoader(showLoader: DataState<Any>?){
    val isLoaderVisible = remember { mutableStateOf(false) }
    when (showLoader) {
        is DataState.Success<Any> -> {
            isLoaderVisible.value = false
        }
        is DataState.Error -> {
            isLoaderVisible.value = false
        }
        is DataState.Loading -> {
            isLoaderVisible.value = true
        }
        else -> {
            isLoaderVisible.value = true
        }
    }
    LoaderShowing(isLoaderVisible = isLoaderVisible)
}
@Composable
fun LoaderShowing(isLoaderVisible: MutableState<Boolean>) {
    if(isLoaderVisible.value) {
        val angle = remember { mutableStateOf(0) }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(1f)
        ) {
            Image(
                imageVector = Icons.Filled.Refresh,
                colorFilter = ColorFilter.tint(Color.Blue.copy(alpha = 0.7f)),
                contentDescription = "", modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .size(40.dp)
                    .fillMaxHeight(1f)
                    .rotate(((angle.value * 10) % 360).toFloat())
            )
        }

        angle.value++
    }
}