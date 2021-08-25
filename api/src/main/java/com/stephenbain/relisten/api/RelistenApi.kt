package com.stephenbain.relisten.api

interface RelistenApi {
    suspend fun getArtists(): List<ArtistWithCountsJson>
    suspend fun getRecentShows(): List<ShowJson>
}

interface ArtistJson {
    val id: Int
    val name: String
    val featured: Int
}

interface ArtistWithCountsJson : ArtistJson {
    val showCount: Int
    val recordingCount: Int
}

interface ShowJson {
    val artist: ArtistJson
    val id: Int
    val displayDate: String
    val duration: Int
}