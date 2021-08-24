package com.stephenbain.relisten.api

interface RelistenApi {
    suspend fun getArtists(): List<ArtistJson>
    suspend fun getRecentShows(): List<ShowJson>
}

interface ArtistJson {
    val id: Int
    val name: String
}

interface ShowJson {
    val artist: ArtistJson
    val id: Int
    val displayDate: String
    val duration: Int
}