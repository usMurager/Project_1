package com.example.project1.network.api

import com.example.project1.data.models.Joke
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface JokesApi {

    @GET("jokes/random")
    fun getNextJoke(): Deferred<Response<Joke>>

}