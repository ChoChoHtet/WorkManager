package com.android.workmanagerexample.database

import android.arch.persistence.room.*

@Dao
interface JokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addJoke(data:Joke)

    @Query("SELECT * FROM Joke")
    fun getAllJoke():List<Joke>

    @Delete
    fun deleteJoke(joke: Joke)

    @Update
    fun updateJoke(joke: Joke)

    @Query("SELECT COUNT(type) FROM joke")
    fun sizeOfJoke():Int

}