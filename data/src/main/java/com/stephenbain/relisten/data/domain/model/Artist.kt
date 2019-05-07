package com.stephenbain.relisten.data.domain.model

import com.stephenbain.relisten.data.api.model.ArtistJson


data class Artist(val name: String)

internal fun ArtistJson.toArtist() = Artist(name = name)