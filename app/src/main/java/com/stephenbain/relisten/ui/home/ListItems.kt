package com.stephenbain.relisten.com.stephenbain.relisten.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
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