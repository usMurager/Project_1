package com.example.project1.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.project1.data.models.Joke

@Dao
interface JokesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLikedJoke(joke: Joke)

    @Query("SELECT * FROM joke_table_test")
    fun getAllData(): LiveData<List<Joke>>

    @Query("DELETE FROM joke_table_test")
    fun clearDatabase()
}