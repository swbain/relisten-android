package com.stephenbain.relisten.domain

import com.stephenbain.relisten.api.ArtistJson
import com.stephenbain.relisten.api.RelistenApi
import com.stephenbain.relisten.api.ShowJson
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ExperimentalStdlibApi
class GetHomeItems @Inject constructor(private val api: RelistenApi) {
    operator fun invoke(): Flow<List<HomeItem>> {
        return flow {
            val artistsAndShows = loadArtistsAndShows()
            val artists = artistsAndShows.first
            val shows = artistsAndShows.second
            emit(
                buildList {
                    add(HomeItem.Separator.Featured)
                    addAll(artists.map { it.toArtistItem(true) }.take(2))
                    if (shows.isNotEmpty()) {
                        add(HomeItem.Separator.LatestRecordings)
                        add(HomeItem.LatestRecordings(shows.map(ShowJson::toHomeRecordingItem)))
                    }
                    add(HomeItem.Separator.AllArtists(artists.size))
                    addAll(artists.map(ArtistJson::toArtistItem))
                }
            )
        }
    }

    private suspend fun loadArtistsAndShows(): Pair<List<ArtistJson>, List<ShowJson>> {
        return coroutineScope {
            val artists = async { api.getArtists() }
            val shows = async { api.getRecentShows() }
            Pair(artists.await(), shows.await())
        }
    }
}

fun ArtistJson.toArtistItem(featured: Boolean = false): HomeItem.ArtistItem {
    return HomeItem.ArtistItem(name = name, featured = featured)
}

fun ShowJson.toHomeRecordingItem(): HomeRecordingItem = HomeRecordingItem(
    name = displayDate + " ${artist.name}"
)

sealed class HomeItem {
    sealed class Separator : HomeItem() {
        object Featured : Separator()
        object LatestRecordings : Separator()
        data class AllArtists(val count: Int) : Separator()
    }
    data class ArtistItem(val name: String, val featured: Boolean = false) : HomeItem()
    data class LatestRecordings(val recordings: List<HomeRecordingItem>) : HomeItem()
}

data class HomeRecordingItem(val name: String)