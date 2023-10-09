package com.example.tweetapp.screens.generalscreens

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.Job

@Composable
fun HomeAppBar(title: String, icon : ImageVector, iconTask: () -> Job) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                iconTask()
            }) {
                Icon(icon, "Menu")
            }
        }
    )
}