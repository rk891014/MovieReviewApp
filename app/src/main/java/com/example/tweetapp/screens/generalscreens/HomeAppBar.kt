package com.example.tweetapp.screens.generalscreens

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.Job

@Composable
fun HomeAppBar(title: String, icon : ImageVector, iconTask: () -> Job) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6, color = Color.White
            )
        },
        backgroundColor = Color.Blue.copy(alpha = 0.6f),
        navigationIcon = {
            IconButton(onClick = {
                iconTask()
            }) {
                Icon(icon, "Menu", tint = Color.White)
            }
        }
    )
}