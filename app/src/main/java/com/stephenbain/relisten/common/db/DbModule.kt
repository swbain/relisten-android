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
    fun providesArtistDao(db: AppDatabase): ArtistDao {
        return db.artistDao()
    }

    @Provides
    @Singleton
    fun providesAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()
    }
}
