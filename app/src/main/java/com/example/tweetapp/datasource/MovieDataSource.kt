package com.example.tweetapp.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tweetapp.api.ApiService
import com.example.tweetapp.model.movies.MovieData
import com.example.tweetapp.model.movies.Result
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieDataSource @Inject constructor(private val apiService: ApiService,private val genreId : Int) :
        PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val nextPage = params.key ?: 1
            val movieResult : MovieData = if(genreId == -1) apiService.getAllMovies(nextPage)
                                            else apiService.getCategorizedMovies(genreId,nextPage)
            movieResult.results.let {list->
                LoadResult.Page(
                    data = list,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey =  if (movieResult.results.isNotEmpty()) movieResult.page.plus(1) else  null
                )
            }
        } catch (exception: IOException) {
//            Timber.e("exception ${exception.message}")
            return LoadResult.Error(exception)
        } catch (httpException: HttpException) {
//            Timber.e("httpException ${httpException.message}")
            return LoadResult.Error(httpException)
        }
    }

}