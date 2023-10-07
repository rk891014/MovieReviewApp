package com.example.tweetapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tweetapp.api.ApiService
import com.example.tweetapp.datasource.MovieDataSource
import com.example.tweetapp.model.artist.Artist
import com.example.tweetapp.model.artist.Cast
import com.example.tweetapp.model.artistdetails.ArtistDetail
import com.example.tweetapp.model.movies.MovieData
import com.example.tweetapp.model.movies.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService) {

//    suspend fun movieDetailList(pageNo : Int): Flow<DataState<MovieDetail>> = flow {
//        emit(DataState.Loading)
//        try {
//            val movieResult = apiService.getCategories(pageNo)
//            emit(DataState.Success(movieResult))
//
//        } catch (e: Exception) {
//            emit(DataState.Error(e))
//        }
//    }

    fun movieDetailList(): Flow<PagingData<Result>> = Pager(
        pagingSourceFactory = { MovieDataSource(apiService) },
        config = PagingConfig(pageSize = 10)
    ).flow

    suspend fun movieDetail(movieId: Int): Flow<DataState<Result>> = flow {
        emit(DataState.Loading)
        try {
            val movieResult = apiService.getMovie(movieId)
            emit(DataState.Success(movieResult))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun recommendedMovie(movieId: Int): Flow<DataState<MovieData>> = flow {
        emit(DataState.Loading)
        try {
            val movieResult = apiService.recommendedMovie(movieId)
            emit(DataState.Success(movieResult))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun movieCredit(movieId: Int): Flow<DataState<Artist>> = flow {
        emit(DataState.Loading)
        try {
            val movieResult = apiService.movieCredit(movieId)
            emit(DataState.Success(movieResult))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getArtistDetails(artistId: Int): Flow<DataState<ArtistDetail>> = flow {
        emit(DataState.Loading)
        try {
            val artistDetails = apiService.artistDetails(artistId)
            emit(DataState.Success(artistDetails))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }


}