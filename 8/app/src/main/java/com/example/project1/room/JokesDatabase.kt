package com.example.project1.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project1.data.models.Joke

@Database(entities = [Joke::class], version = 1, exportSchema = false)
abstract class JokesDatabase: RoomDatabase() {

    abstract fun jokeDao(): JokesDao

    companion object{
        @Volatile
        private var INSTANCE: JokesDatabase? = null

        fun getJokesDatabase(context: Context): JokesDatabase {
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JokesDatabase::class.java,
                    "jokes_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}