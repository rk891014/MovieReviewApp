package com.example.tweetapp.screens.mainscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tweetapp.model.moviegenre.MovieGenres
import com.example.tweetapp.model.movies.Result
import com.example.tweetapp.repository.DataState
import com.example.tweetapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    val genreList: MutableState<DataState<MovieGenres>?> = mutableStateOf(null)
    val movieList : MutableState<Flow<PagingData<Result>>?> = mutableStateOf(null)


    fun moviePagingList(genre: Int) {
        movieList.value = repository.movieDetailList(genre).cachedIn(viewModelScope)
    }

    fun getMovieGenreList() {
        viewModelScope.launch {
            repository.movieGenreList().onEach {
                genreList.value = it
            }.launchIn(viewModelScope)
        }
    }




//    fun movieList(movieId : String) {
//        viewModelScope.launch {
//            repository.movieDetailList(2).onEach {
//                movieData.value = it
//            }.launchIn(viewModelScope)
//        }
//    }

}