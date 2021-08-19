package com.stephenbain.relisten.domain

import com.stephenbain.relisten.api.ArtistJson
import com.stephenbain.relisten.api.RelistenApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ExperimentalStdlibApi
class GetHomeItems @Inject constructor(private val api: RelistenApi) {
    operator fun invoke(): Flow<List<HomeItem>> {
        return flow {
            val artists = api.getArtists()
            emit(
                buildList {
                    add(HomeItem.Separator.AllArtists(artists.size))
                    addAll(artists.map(ArtistJson::toArtistItem))
                }
            )
        }
    }
}

fun ArtistJson.toArtistItem(): HomeItem.ArtistItem = HomeItem.ArtistItem(name = name)

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