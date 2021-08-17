package com.stephenbain.relisten.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetHomeItems @Inject constructor() {
    @OptIn(ExperimentalStdlibApi::class)
    operator fun invoke(): Flow<List<HomeItem>> {
        return flowOf(
            listOf(
                HomeItem.Separator.Featured,
                HomeItem.ArtistItem("Grateful Dead"),
                HomeItem.ArtistItem("Phish"),
                HomeItem.Separator.LatestRecordings,
                HomeItem.LatestRecordings(
                    buildList {
                        for (i in 1..7) {
                            add(HomeRecordingItem("phish show $i"))
                        }
                    }
                )
            )
        )
    }
}

sealed class HomeItem {
    sealed class Separator : HomeItem() {
        object Featured : Separator()
        object LatestRecordings : Separator()
        data class AllArtists(val count: Int) : Separator()
    }
    data class ArtistItem(val name: String) : HomeItem()
    data class LatestRecordings(val recordings: List<HomeRecordingItem>) : HomeItem()
}

data class HomeRecordingItem(val name: String)