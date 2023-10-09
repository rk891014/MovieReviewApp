package com.example.tweetapp.screens.searchscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tweetapp.model.movies.MovieData
import com.example.tweetapp.repository.DataState
import com.example.tweetapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedSearchViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    val searchedMovieList: MutableState<DataState<MovieData>?> = mutableStateOf(null)
    fun searchApi(searchedText: String) {
        viewModelScope.launch {
            repository.getSearchedMovieList(searchedText).onEach {
                searchedMovieList.value = it
            }.launchIn(viewModelScope)
        }
    }
}