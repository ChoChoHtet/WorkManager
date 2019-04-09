package com.android.workmanagerexample.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Joke::class], version = 1)
abstract class JokeDatabase : RoomDatabase() {
    abstract fun getJokeDao(): JokeDao

    companion object {
        private var instance: JokeDatabase? = null
        fun provideDatabase(context: Context): JokeDatabase {
            synchronized(this) {
                if (instance == null)
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        JokeDatabase::class.java, "weather"
                    ).allowMainThreadQueries().build()
                return instance!!
            }

        }
    }

}