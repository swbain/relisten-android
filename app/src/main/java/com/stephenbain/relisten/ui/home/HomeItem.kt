package com.stephenbain.relisten.ui.home

import com.stephenbain.relisten.repository.Artist


sealed class HomeItem {
    data class Divider(val title: HomeTitle) : HomeItem()
    data class ArtistItem(val artist: Artist) : HomeItem()
}

enum class HomeTitle {
    RECENTLY_PLAYED, FEATURED, LATEST_RECORDINGS, ALL_ARTISTS
}