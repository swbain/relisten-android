package com.stephenbain.api

import com.stephenbain.relisten.api.ArtistWithCountsJson
import com.stephenbain.relisten.api.RelistenApi
import com.stephenbain.relisten.api.ShowJson
import javax.inject.Inject

internal class RelistenApiRetrofit @Inject constructor(
    private val apiService: ApiService
) : RelistenApi {
    override suspend fun getArtists(): List<ArtistWithCountsJson> = apiService.getArtists()

    override suspend fun getRecentShows(): List<ShowJson> = apiService.getRecentShows()
}
