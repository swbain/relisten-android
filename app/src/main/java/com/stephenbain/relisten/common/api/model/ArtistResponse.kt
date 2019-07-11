package com.stephenbain.relisten.common.api.model

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson


data class ArtistJson(val id: Int, val name: String, val featured: Int)

data class ArtistResponse(val id: Int, val name: String, val isFeatured: Boolean)

class ArtistJsonAdapter {

    @FromJson
    fun artistFromJson(json: ArtistJson): ArtistResponse = ArtistResponse(
        id = json.id,
        name = json.name,
        isFeatured = json.featured == 1
    )

    @ToJson
    fun artistToJson(artist: ArtistResponse): ArtistJson = ArtistJson(
        id = artist.id,
        name = artist.name,
        featured = if (artist.isFeatured) 1 else 0
    )
}