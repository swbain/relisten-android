package com.stephenbain.relisten.api

interface RelistenApi {
    suspend fun getArtists(): List<ArtistJson>
}

interface ArtistJson{
    val id: Int
    val name: String
}