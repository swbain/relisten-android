package com.stephenbain.relisten.common.api

import com.stephenbain.relisten.common.Artist
import io.reactivex.Single
import retrofit2.http.GET


interface RelistenApi {
    @GET("artists")
    fun getArtists(): Single<List<Artist>>
}