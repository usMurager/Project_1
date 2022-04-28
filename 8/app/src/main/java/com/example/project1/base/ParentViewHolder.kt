package com.example.project1.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ParentViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    protected abstract fun clear()
}