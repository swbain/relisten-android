package com.stephenbain.relisten.data.api

import com.stephenbain.relisten.data.api.model.ArtistJson
import io.reactivex.Single
import retrofit2.http.GET


internal interface RelistenApi {
    @GET("artists")
    fun getArtists(): Single<List<ArtistJson>>
}