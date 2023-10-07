package com.example.tweetapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tweetapp.navigation.AppNavigationController
import com.example.tweetapp.ui.theme.TweetAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TweetAppTheme {
                AppNavigationController()
            }
        }
    }
}