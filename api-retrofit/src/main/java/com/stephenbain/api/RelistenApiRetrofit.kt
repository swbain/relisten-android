package com.stephenbain.api

import com.stephenbain.relisten.api.ArtistJson
import com.stephenbain.relisten.api.RelistenApi
import javax.inject.Inject

internal class RelistenApiRetrofit @Inject constructor(
    private val apiService: ApiService
) : RelistenApi {
    override suspend fun getArtists(): List<ArtistJson> = apiService.getArtists()
}
