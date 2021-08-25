package com.stephenbain.relisten.com.stephenbain.relisten.ui.home

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stephenbain.relisten.R
import com.stephenbain.relisten.domain.HomeItem
import com.stephenbain.relisten.domain.HomeRecordingItem
import com.stephenbain.relisten.domain.HomeSeparator
import com.stephenbain.relisten.ui.theme.LighterGray
import com.stephenbain.relisten.ui.theme.Typography

@ExperimentalMaterialApi
@Composable
fun ArtistListEntry(item: HomeItem.ArtistItem) {
    ListItem(
        text = { Text(item.name) },
        secondaryText = { Text("${item.showCount} shows") },
        trailing = { Text("${item.recordingCount} recordings") },
        icon = { ArtistListFavIcon(item.isFavorite) },
        modifier = Modifier.clickable {  }
    )
}

@Composable
fun ArtistListFavIcon(isFavorite: Boolean) {
    Box(
        modifier = Modifier.size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "favorite",
            tint = if (isFavorite) Color.Red else Color.LightGray
        )
    }
}

@ExperimentalMaterialApi
@Preview(name = "Artist List Entry", showBackground = true)
@Composable
fun ArtistListEntry() {
    ArtistListEntry(
        item = HomeItem.ArtistItem(
            name = "grateful dead",
            isFavorite = false,
            showCount = 1200,
            recordingCount = 3000,
            featured = false
        )
    )
}

@Composable
fun LatestRecordingsListEntry(item: HomeItem.LatestRecordings) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(items = item.recordings, key = HomeRecordingItem::id) {
            LatestRecordingItem(it)
        }
    }
}

@Composable
fun LatestRecordingItem(item: HomeRecordingItem) {
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        elevation = 0.dp
    ) {
        Column {
            Text(text = item.artistName, style = Typography.overline)
            Text(text = item.date, style = Typography.h6)
            Text(text = item.city, style = Typography.caption)
            Text(text = item.formattedDuration, style = Typography.caption)
        }
    }
}

val HomeRecordingItem.formattedDuration: String
    get() = DateUtils.formatElapsedTime(durationSeconds)

@Composable
fun SeparatorListEntry(item: HomeSeparator) {
    Box(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.surface),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = when (item) {
                is HomeSeparator.AllArtists -> stringResource(R.string.all_artists, item.count)
                HomeSeparator.Featured -> stringResource(id = R.string.featured)
                HomeSeparator.LatestRecordings -> stringResource(id = R.string.latest_recordings)
            }
        )
    }
}