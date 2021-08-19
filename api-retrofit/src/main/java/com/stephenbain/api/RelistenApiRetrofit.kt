package com.stephenbain.api

import com.stephenbain.relisten.api.ArtistJson
import com.stephenbain.relisten.api.RelistenApi
import javax.inject.Inject

internal class RelistenApiRetrofit @Inject constructor() : RelistenApi {
    override suspend fun getArtists(): List<ArtistJson> {
        return listOf(ArtistJson(id = 0, name = "grateful dead"))
    }
}