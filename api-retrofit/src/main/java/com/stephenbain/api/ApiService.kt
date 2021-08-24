package com.stephenbain.api

import com.squareup.moshi.JsonClass
import com.stephenbain.relisten.api.ArtistJson
import retrofit2.http.GET

internal interface ApiService {
    @GET("artists")
    suspend fun getArtists(): List<ArtistJsonImpl>
}

@JsonClass(generateAdapter = true)
internal data class ArtistJsonImpl(override val id: Int, override val name: String) : ArtistJson