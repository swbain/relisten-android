package com.stephenbain.relisten.api

import com.stephenbain.relisten.api.model.ArtistJson
import io.reactivex.Single
import retrofit2.http.GET


interface RelistenApi {
    @GET("artists")
    fun getArtists(): Single<List<ArtistJson>>
}