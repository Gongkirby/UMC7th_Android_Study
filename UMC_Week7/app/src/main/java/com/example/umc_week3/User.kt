package com.example.umc_week3

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    var email : String,
    var password : String
) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
