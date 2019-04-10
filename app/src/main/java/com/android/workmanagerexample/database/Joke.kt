package com.android.workmanagerexample.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Joke(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val type: String?,
    val setup:String?,
    val punchline:String?
)