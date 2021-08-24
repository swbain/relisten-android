package com.stephenbain.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.stephenbain.relisten.api.ArtistJson
import com.stephenbain.relisten.api.ShowJson
import retrofit2.http.GET

internal interface ApiService {
    @GET("artists")
    suspend fun getArtists(): List<ArtistJsonImpl>

    @GET("shows/recently-added")
    suspend fun getRecentShows(): List<ShowJsonImpl>
}

@JsonClass(generateAdapter = true)
internal data class ArtistJsonImpl(
    override val id: Int,
    override val name: String,
) : ArtistJson

@JsonClass(generateAdapter = true)
internal data class ShowJsonImpl(
    override val artist: ArtistJsonImpl,
    override val id: Int,
    @Json(name = "display_date")
    override val displayDate: String,
    @Json(name = "avg_duration")
    override val duration: Int,
) : ShowJson