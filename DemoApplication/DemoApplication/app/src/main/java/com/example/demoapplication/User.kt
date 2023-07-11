package com.example.demoapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val name: String,
    val gender: String,
    val country: String,
    val city: String,
    val email: String,
    val cell: String,
    val picture: String,
    val age : Int,
    var status : String
)