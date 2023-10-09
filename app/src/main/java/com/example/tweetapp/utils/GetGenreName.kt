package com.example.tweetapp.utils

import com.example.tweetapp.model.moviegenre.Genre

object GetGenreName {

    private val genreMap = HashMap<Int,String>()

    fun setGenre(genres : ArrayList<Genre>){
        genres.forEach {
            genreMap[it.id] = it.name
        }
    }

    fun getGenres() : HashMap<Int, String> {
        return genreMap
    }
}