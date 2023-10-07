package com.example.tweetapp.model.movies

data class MovieData(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)