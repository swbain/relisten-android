package com.stephenbain.relisten.domain

import com.stephenbain.relisten.api.ArtistWithCountsJson
import com.stephenbain.relisten.api.RelistenApi
import com.stephenbain.relisten.api.ShowJson
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
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
                    artists.filter { it.featured == 1 }
                        .map { it.toArtistItem(featured = true) }
                        .let(::addAll)

                    if (shows.isNotEmpty()) {
                        add(HomeItem.Separator.LatestRecordings)
                        add(HomeItem.LatestRecordings(shows.map(ShowJson::toHomeRecordingItem)))
                    }

                    add(HomeItem.Separator.AllArtists(artists.size))
                    addAll(artists.map(ArtistWithCountsJson::toArtistItem))
                }
            )
        }
    }

    private suspend fun loadArtistsAndShows(): Pair<List<ArtistWithCountsJson>, List<ShowJson>> {
        return coroutineScope {
            val artists = async { api.getArtists() }
            val shows = async { api.getRecentShows() }
            Pair(artists.await(), shows.await())
        }
    }
}

fun ArtistWithCountsJson.toArtistItem(
    isFavorite: Boolean = false,
    featured: Boolean = false
): HomeItem.ArtistItem {
    return HomeItem.ArtistItem(
        name = name,
        isFavorite = isFavorite,
        featured = featured,
        showCount = showCount,
        recordingCount = recordingCount
    )
}

@ExperimentalTime
fun ShowJson.toHomeRecordingItem(): HomeRecordingItem = HomeRecordingItem(
    date = displayDate,
    artistName = artist.name,
    city = venue.location,
    durationSeconds = duration,
    id = id,
)

sealed class HomeItem {
    sealed class Separator : HomeItem() {
        object Featured : Separator()
        object LatestRecordings : Separator()
        data class AllArtists(val count: Int) : Separator()
    }

    data class ArtistItem(
        val name: String,
        val isFavorite: Boolean,
        val showCount: Int,
        val recordingCount: Int,
        val featured: Boolean = false,
    ) : HomeItem()

    data class LatestRecordings(val recordings: List<HomeRecordingItem>) : HomeItem()
}

data class HomeRecordingItem(
    val artistName: String,
    val date: String,
    val city: String,
    val durationSeconds: Long,
    val id: Int
)