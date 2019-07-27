package com.stephenbain.relisten.common.api.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.stephenbain.relisten.common.Artist

data class ArtistJson(val id: Int, val name: String, val featured: Int)

class ArtistJsonAdapter {

    @FromJson
    fun artistFromJson(json: ArtistJson): Artist = Artist(
        id = json.id,
        name = json.name,
        isFeatured = json.featured == 1
    )

    @ToJson
    fun artistToJson(artist: Artist): ArtistJson =
        ArtistJson(
            id = artist.id,
            name = artist.name,
            featured = if (artist.isFeatured) 1 else 0
        )
}
