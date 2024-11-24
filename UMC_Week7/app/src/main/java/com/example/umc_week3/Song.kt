package com.example.umc_week3

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "SongTable",
    foreignKeys = [ForeignKey(
        entity = Album::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("albumIdx"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Song(
    var title: String = "",
    var singer: String = "",
    var coverImg: Int? = null,
    var second: Int = 0,
    var playTime: Int = 0,
    var isPlaying: Boolean = false,
    var music: String = "",
    var isLike: Boolean = false,
    var albumIdx: Int = 0
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}