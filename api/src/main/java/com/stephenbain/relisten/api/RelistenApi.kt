package com.stephenbain.relisten.api

interface RelistenApi {
    suspend fun getArtists(): List<ArtistJson>
}

data class ArtistJson(val id: Int, val name: String)