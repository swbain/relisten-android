package com.stephenbain.relisten.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stephenbain.relisten.R
import com.stephenbain.relisten.com.stephenbain.relisten.ui.common.LoadingErrorList
import com.stephenbain.relisten.domain.HomeItem
import com.stephenbain.relisten.domain.HomeRecordingItem

@ExperimentalStdlibApi
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    LoadingErrorList(
        state = viewModel.state.collectAsState().value,
        itemContent = { HomeItem(it) },
        error = { HomeError(viewModel::loadData) },
        key = HomeItem::key
    )
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
    LazyRow(modifier = Modifier
        .height(80.dp)
        .fillMaxWidth()) {
        items(items = item.recordings, key = HomeRecordingItem::name) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(70.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(it.name)
            }
        }
    }
}

@Composable
fun SeparatorListEntry(item: HomeItem.Separator) {
    Text(
        text = when (item) {
            is HomeItem.Separator.AllArtists -> stringResource(R.string.all_artists, item.count)
            HomeItem.Separator.Featured -> stringResource(id = R.string.featured)
            HomeItem.Separator.LatestRecordings -> stringResource(id = R.string.latest_recordings)
        }
    )
}

@Composable
fun HomeError(onRetryClick: () -> Unit) = Box(
    modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
    contentAlignment = Alignment.Center,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(stringResource(id = R.string.error))
        Button(onClick = onRetryClick) {
            Text(stringResource(id = R.string.retry))
        }
    }
}

val HomeItem.key: String
    get() = when (this) {
        is HomeItem.ArtistItem -> "artist=$name featured=$featured"
        is HomeItem.LatestRecordings -> "latest_recordings_carousel"
        is HomeItem.Separator.AllArtists -> "all_artists_separator"
        HomeItem.Separator.Featured -> "featured_separator"
        HomeItem.Separator.LatestRecordings -> "latest_recordings_separator"
    }