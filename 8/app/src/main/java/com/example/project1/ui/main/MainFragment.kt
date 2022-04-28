package com.example.project1.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.project1.R
import com.example.project1.base.ParentFragment
import com.example.project1.data.models.Joke
import org.koin.android.ext.android.inject

class MainFragment  : ParentFragment() {

    private val viewModel: MainViewModel by inject()
    private val jokesViewModel: JokesViewModel by inject()

    private lateinit var tvJoke: TextView
    private lateinit var btnLoad: Button
    private lateinit var btnLike: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setData()
    }

    override fun bindViews(view: View) = with(view) {
        tvJoke = view.findViewById(R.id.tv_joke)
        btnLoad = view.findViewById(R.id.btn_load)
        btnLike = view.findViewById(R.id.btn_like)

        btnLike.setOnClickListener{
            if(currentJoke != null){
                addToDatabase(currentJoke)
            }
        }

        btnLoad.setOnClickListener{
            jokesViewModel.getNextJoke()
        }

    }

    override fun setData() {
        observeJokeViewModel()
    }

    var currentJoke: Joke? = null
    fun addToDatabase(item: Joke?){
        if(item != null){
            viewModel.addToDatabase(item)
        }
    }
    private fun observeJokeViewModel() {
        jokesViewModel.getNextJoke()

        jokesViewModel.liveData.observe(
            viewLifecycleOwner,
            Observer {result ->
                when (result) {
                    is JokesViewModel.State.ShowLoading -> {
                        tvJoke.text = "Loading"
                    }
                    is JokesViewModel.State.Result -> {
                        tvJoke.text = result.joke?.value
                        currentJoke = result.joke
                    }
                    is JokesViewModel.State.Error -> {
                    }
                }
            }
        )
    }

}