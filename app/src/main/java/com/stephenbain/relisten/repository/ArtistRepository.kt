package com.stephenbain.relisten.repository

import com.stephenbain.relisten.api.RelistenApi
import com.stephenbain.relisten.api.model.ArtistResponse
import io.reactivex.Observable
import io.reactivex.rxkotlin.concatMapIterable
import javax.inject.Inject


class ArtistRepository @Inject constructor(private val relistenApi: RelistenApi) {

    fun getArtists(): Observable<List<Artist>> {
        return relistenApi.getArtists()
            .toObservable()
            .concatMapIterable()
            .map { it.toArtist() }
            .toList()
            .toObservable()
    }

    private fun ArtistResponse.toArtist(): Artist = Artist(
        id = id,
        name = name,
        isFeatured = isFeatured,
        isFavorite = false
    )

}

data class Artist(val id: Int, val name: String, val isFeatured: Boolean, val isFavorite: Boolean)