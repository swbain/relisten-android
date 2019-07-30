package com.stephenbain.relisten.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stephenbain.relisten.common.Artist
import com.stephenbain.relisten.common.Show

@Database(entities = [Artist::class, Show::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistDao
    abstract fun recentlyAddedDao(): RecentlyAddedDao
}
