package com.stephenbain.relisten.common.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stephenbain.relisten.common.Show
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface RecentlyAddedDao {

    @Query("SELECT * FROM shows")
    fun getRecentlyAddedShows(): Observable<List<Show>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putRecentlyAddedShows(shows: List<Show>): Completable
}
