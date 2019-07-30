package com.stephenbain.relisten.common.api

import com.stephenbain.relisten.common.Artist
import com.stephenbain.relisten.common.Show
import io.reactivex.Single
import retrofit2.http.GET

interface RelistenApi {
    @GET("artists")
    fun getArtists(): Single<List<Artist>>

    @GET("shows/recently-added")
    fun getRecentlyAddedShows(): Single<List<Show>>
}
