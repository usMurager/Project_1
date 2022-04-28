package com.example.project1.network.repository

import com.example.project1.data.models.Joke

interface JokesRepository {

    suspend fun getNextJoke(): Joke?

}