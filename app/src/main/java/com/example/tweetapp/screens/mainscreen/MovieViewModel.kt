package com.example.tweetapp.screens.mainscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tweetapp.model.movies.MovieData
import com.example.tweetapp.model.movies.Result
import com.example.tweetapp.repository.DataState
import com.example.tweetapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    val movieData: MutableState<DataState<MovieData>?> = mutableStateOf(null)

    fun moviePagingList() : Flow<PagingData<Result>> {
        return repository.movieDetailList().cachedIn(viewModelScope)
    }


//    fun movieList(movieId : String) {
//        viewModelScope.launch {
//            repository.movieDetailList(2).onEach {
//                movieData.value = it
//            }.launchIn(viewModelScope)
//        }
//    }

}