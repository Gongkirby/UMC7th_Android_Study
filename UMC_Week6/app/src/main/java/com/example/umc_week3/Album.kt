package com.example.umc_week3

import com.example.flo.Song

data class Album(
    var title : String? = "",
    var singer : String? = "",
    var coverImg : Int? = null,
    var songs : ArrayList<Song>? = null
)