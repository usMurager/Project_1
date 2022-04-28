package com.example.project1.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.example.project1.base.ParentViewModel
import com.example.project1.data.models.Joke
import com.example.project1.room.JokesDatabase
import com.example.project1.room.JokesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : ParentViewModel() {

    var readAllData: LiveData<List<Joke>>
    private val repository: JokesRepository

    init {
        val todoDao = JokesDatabase.getJokesDatabase(app).jokeDao()
        repository = JokesRepository(todoDao)
        readAllData = repository.getAllData
    }

    fun addToDatabase(item: Joke?) {
        if (item != null) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addLikedJoke(
                    item
                )
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearDatabase()
        }
    }

    override fun handleError(e: Throwable) {

    }
}