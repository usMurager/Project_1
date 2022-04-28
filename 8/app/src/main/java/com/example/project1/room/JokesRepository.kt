package com.example.project1.room

import androidx.lifecycle.LiveData
import com.example.project1.data.models.Joke

class JokesRepository(private val jokesDao: JokesDao) {

    val getAllData: LiveData<List<Joke>> = jokesDao.getAllData()

    suspend fun addLikedJoke(joke: Joke){
        jokesDao.addLikedJoke(joke)
    }

    fun clearDatabase(){
        jokesDao.clearDatabase()
    }
}