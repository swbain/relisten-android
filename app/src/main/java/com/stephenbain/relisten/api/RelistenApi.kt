package com.stephenbain.relisten.api

import com.stephenbain.relisten.api.model.ArtistResponse
import io.reactivex.Single
import retrofit2.http.GET


interface RelistenApi {
    @GET("artists")
    fun getArtists(): Single<List<ArtistResponse>>
}