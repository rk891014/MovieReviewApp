package com.example.tweetapp.screens.artistinfo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tweetapp.model.artist.Cast
import com.example.tweetapp.model.artistdetails.ArtistDetail
import com.example.tweetapp.repository.DataState
import com.example.tweetapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistInfoViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    val artistDetails : MutableState<DataState<ArtistDetail>?> = mutableStateOf(null)

    fun getArtistDetails(artistId: Int?) {
        artistId?.let {id->
            viewModelScope.launch {
                repository.getArtistDetails(id).onEach {
                    artistDetails.value = it
                }.launchIn(viewModelScope)
            }
        }
    }

}

