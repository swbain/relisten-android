package com.stephenbain.relisten.common.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun providesArtistDao(context: Context): ArtistDao {
        return Room.databaseBuilder(context, AppDatabase::class.java, "db").build().artistDao()
    }

}