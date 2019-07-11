package com.stephenbain.relisten.domain.model

import com.stephenbain.relisten.api.model.ArtistResponse
import com.stephenbain.relisten.repository.Artist


sealed class HomeSection {
    data class FeaturedArtists(val artists: List<Artist>) : HomeSection()
    data class AllArtists(val artists: List<Artist>) : HomeSection()
}