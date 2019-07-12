package com.stephenbain.relisten.home.ui

import com.stephenbain.relisten.common.Artist


sealed class HomeItem {
    data class Divider(val title: HomeTitle) : HomeItem()
    data class ArtistItem(val artist: Artist) : HomeItem()
}

enum class HomeTitle {
    RECENTLY_PLAYED, FEATURED, LATEST_RECORDINGS, ALL_ARTISTS
}