package com.stephenbain.relisten.common.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stephenbain.relisten.common.Artist
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface ArtistDao {

    @Query("SELECT * FROM artist")
    fun getAllArtists(): Observable<List<Artist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putArtists(artists: List<Artist>): Completable
}
