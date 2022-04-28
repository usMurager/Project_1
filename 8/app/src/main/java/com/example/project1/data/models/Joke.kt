package com.example.project1.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "joke_table_test")
data class Joke(
    @PrimaryKey val id: String,
    val icon_url: String?,
    val url: String?,
    val value: String?
)
