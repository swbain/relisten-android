package com.stephenbain.relisten.ui.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stephenbain.relisten.domain.HomeItem

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) =
    when (val state = viewModel.state.collectAsState().value) {
        is HomeState.Error -> Text("Error")
        HomeState.Loading -> Text("Loading")
        is HomeState.Success -> HomeList(state.items)
    }

@Composable
fun HomeList(homeItems: List<HomeItem>) {
    LazyColumn {
        items(homeItems, key = HomeItem::key) { item ->
            HomeItem(item)
        }
    }
}

@Composable
fun HomeItem(item: HomeItem) = when (item) {
    is HomeItem.ArtistItem -> ArtistListEntry(item)
    is HomeItem.LatestRecordings -> LatestRecordingsListEntry(item)
    is HomeItem.Separator -> SeparatorListEntry(item)
}

@Composable
fun ArtistListEntry(item: HomeItem.ArtistItem) {
    Text(item.name)
}

@Composable
fun LatestRecordingsListEntry(item: HomeItem.LatestRecordings) {
    Text("latest recordings")
}

@Composable
fun SeparatorListEntry(item: HomeItem.Separator) {
    Text(
        text = when (item) {
            is HomeItem.Separator.AllArtists -> "${item.count} artists"
            HomeItem.Separator.Featured -> "featured"
            HomeItem.Separator.LatestRecordings -> "latest recordings"
        }
    )
}

val HomeItem.key: String
    get() = when (this) {
        is HomeItem.ArtistItem -> "artist_$name"
        is HomeItem.LatestRecordings -> "latest_recordings_carousel"
        is HomeItem.Separator.AllArtists -> "all_artists_separator"
        HomeItem.Separator.Featured -> "featured_separator"
        HomeItem.Separator.LatestRecordings -> "latest_recordings_separator"
    }