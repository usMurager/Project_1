package com.example.project1.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.project1.additonal.ConnectionFailedException
import com.example.project1.additonal.launchSafe
import com.example.project1.base.ParentViewModel
import com.example.project1.data.models.Joke
import com.example.project1.network.repository.JokesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JokesViewModel (private val jokesRepository: JokesRepository) : ParentViewModel() {

    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        data class Result(val joke: Joke?) : State()
        data class Error(val error: String?) : State()
        data class IntError(val error: Int) : State()
    }

    private val _liveData = MutableLiveData<State>()
    val liveData: LiveData<State>
        get() = _liveData

    fun getNextJoke() {
        _liveData.value =
            State.ShowLoading
        uiScope.launchSafe(::handleError) {
            val result = withContext(Dispatchers.IO) {
                jokesRepository.getNextJoke()
            }
            _liveData.postValue(
                State.Result(result)
            )
        }
        _liveData.value =
            State.HideLoading

    }

    override fun handleError(e: Throwable) {
        _liveData.value = State.HideLoading
        if (e is ConnectionFailedException) {
            _liveData.value = State.IntError(e.messageInt)
        } else {
            _liveData.value = State.Error(e.localizedMessage)
        }
    }
}