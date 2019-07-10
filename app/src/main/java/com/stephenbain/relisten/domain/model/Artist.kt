package com.stephenbain.relisten.domain.model


data class Artist(val name: String)

internal fun com.stephenbain.relisten.api.model.ArtistJson.toArtist() =
    Artist(name = name)