package com.stephenbain.relisten.home.domain.model

import com.stephenbain.relisten.common.Artist

sealed class HomeSection {
    data class FeaturedArtists(val artists: List<Artist>) : HomeSection()
    data class AllArtists(val artists: List<Artist>) : HomeSection()
}
