package com.example.tweetapp.ui.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SubtitlePrimary(text:String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.body1,
        modifier = modifier
    )
}