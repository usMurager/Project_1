package com.example.project1.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project1.R
import com.example.project1.base.ParentViewHolder
import com.example.project1.data.models.Joke
import java.lang.StringBuilder


class HistoryAdapter :
    RecyclerView.Adapter<ParentViewHolder>() {

    private val VIEW_TYPE_ERROR = 0
    private val VIEW_TYPE_NORMAL = 1

    private val jokes = ArrayList<Joke>()


    fun clearAll() {
        jokes.clear()
        notifyDataSetChanged()
    }


    fun addItems(list: List<Joke>) {
        jokes.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_NORMAL -> CatsViewHolder(
                inflater.inflate(R.layout.adapter_joke, parent, false)
            )
            else -> throw Throwable("invalid view")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Joke -> {
                return VIEW_TYPE_NORMAL
            }
            else -> {
                return VIEW_TYPE_ERROR
            }
        }
    }

    override fun getItemCount(): Int = jokes.size

    fun getItem(position: Int): Joke? {
        return jokes[position]
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        if (holder is CatsViewHolder) {
            val jk = jokes[position]
            holder.bind(jk)
        }
    }

    inner class CatsViewHolder(view: View) : ParentViewHolder(view) {
        private val tvJoke: TextView = view.findViewById(R.id.tv_joke_value)

        fun bind(joke: Joke) {

            val strBuilder = StringBuilder()
                .append("url: " + joke.url + "\n")
                .append("-------joke-------" + "\n")
                .append(joke.value)
            tvJoke.text = strBuilder
        }

        override fun clear() {}
    }

}