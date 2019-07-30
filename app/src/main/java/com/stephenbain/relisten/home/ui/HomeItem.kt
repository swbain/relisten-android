package com.stephenbain.relisten.home.ui

import com.stephenbain.relisten.common.Artist
import com.stephenbain.relisten.common.Show

sealed class HomeItem {
    data class Divider(val title: HomeTitle) : HomeItem()
    data class ArtistItem(val artist: Artist) : HomeItem()
    data class ShowsItem(val shows: List<Show>) : HomeItem()
}

enum class HomeTitle {
    RECENTLY_PLAYED, FEATURED, LATEST_RECORDINGS, ALL_ARTISTS
}
