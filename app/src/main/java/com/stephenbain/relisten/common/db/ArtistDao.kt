package com.stephenbain.relisten.common.db

import com.stephenbain.relisten.common.Artist
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject


class ArtistDao @Inject constructor() {

    fun getAllArtists(): Observable<List<Artist>> = TODO()

    fun putArtists(artists: List<Artist>): Completable = TODO()

}