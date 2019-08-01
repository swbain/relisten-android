package com.stephenbain.relisten.common.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DbModule {

    @Provides
    @Singleton
    @JvmStatic
    fun providesArtistDao(db: AppDatabase): ArtistDao {
        return db.artistDao()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun providesRecentlyAddedDao(db: AppDatabase): RecentlyAddedDao {
        return db.recentlyAddedDao()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun providesAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()
    }
}
