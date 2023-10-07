package com.example.tweetapp.screens.movieInfo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tweetapp.model.artist.Artist
import com.example.tweetapp.model.movies.MovieData
import com.example.tweetapp.model.movies.Result
import com.example.tweetapp.repository.DataState
import com.example.tweetapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    val movieDetail: MutableState<DataState<Result>?> = mutableStateOf(null)
    val recommendedMovie: MutableState<DataState<MovieData>?> = mutableStateOf(null)
    val artist: MutableState<DataState<Artist>?> = mutableStateOf(null)

    fun movieData(movieId: Int) {
        viewModelScope.launch {
            repository.movieDetail(movieId).onEach {
                movieDetail.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun recommendedMovies(movieId: Int) {
        viewModelScope.launch {
            repository.recommendedMovie(movieId).onEach {
                recommendedMovie.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun movieCredit(movieId: Int) {
        viewModelScope.launch {
            repository.movieCredit(movieId).onEach {
                artist.value = it
            }.launchIn(viewModelScope)
        }
    }


}