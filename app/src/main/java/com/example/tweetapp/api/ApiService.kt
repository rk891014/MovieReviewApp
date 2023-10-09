package com.example.tweetapp.api

import com.example.tweetapp.model.artist.Artist
import com.example.tweetapp.model.artistdetails.ArtistDetail
import com.example.tweetapp.model.moviegenre.MovieGenres
import com.example.tweetapp.model.movies.MovieData
import com.example.tweetapp.model.movies.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getAllMovies(
        @Query("page") page : Int
    ) : MovieData
    @GET("discover/movie")
    suspend fun getCategorizedMovies(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): MovieData


    @GET("movie/{movieId}")
    suspend fun getMovie(
        @Path("movieId") movieId: Int
    ): Result

    @GET("movie/{movieId}/recommendations")
    suspend fun recommendedMovie(
        @Path("movieId") movieId: Int,
    ): MovieData

    @GET("movie/{movieId}/credits")
    suspend fun movieCredit(
        @Path("movieId") movieId: Int
    ): Artist


    @GET("person/{artistId}")
    suspend fun artistDetails(
        @Path("artistId") artistId: Int?
    ): ArtistDetail

    @GET("genre/movie/list")
    suspend fun getGenres(): MovieGenres


    @GET("search/movie")
    suspend fun getSearchedMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieData

}