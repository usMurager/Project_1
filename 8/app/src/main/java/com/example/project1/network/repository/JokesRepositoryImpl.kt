package com.example.project1.network.repository

import com.example.project1.data.models.Joke
import com.example.project1.network.api.JokesApi

class JokesRepositoryImpl(private val jokesApi: JokesApi) : JokesRepository {

    override suspend fun getNextJoke(): Joke? {
        return jokesApi.getNextJoke().await().body()
    }
}