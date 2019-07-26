package com.stephenbain.relisten.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stephenbain.relisten.common.Artist

@Database(entities = [Artist::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistDao
}