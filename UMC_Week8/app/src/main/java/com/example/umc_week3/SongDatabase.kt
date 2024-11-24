package com.example.umc_week3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.umc_week3.Album
import com.example.umc_week3.AlbumDao
import com.example.umc_week3.Like
import com.example.umc_week3.Song
import com.example.umc_week3.SongDao
import com.example.umc_week3.User
import com.example.umc_week3.UserDao

@Database(entities = [Song::class, Album::class, User::class, Like::class], version = 2)
abstract class SongDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao
    abstract fun userDao(): UserDao

    companion object {
        private var instance: SongDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SongDatabase? {
            if (instance == null) {
                synchronized(SongDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SongDatabase::class.java,
                        "song-database"

                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }

            return instance
        }
    }
}