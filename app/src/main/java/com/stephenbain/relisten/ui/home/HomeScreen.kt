package com.stephenbain.relisten.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stephenbain.relisten.R
import com.stephenbain.relisten.com.stephenbain.relisten.ui.common.LoadingErrorListWithSeparators
import com.stephenbain.relisten.com.stephenbain.relisten.ui.common.StickyHeaderList
import com.stephenbain.relisten.com.stephenbain.relisten.ui.common.map
import com.stephenbain.relisten.com.stephenbain.relisten.ui.home.ArtistListEntry
import com.stephenbain.relisten.com.stephenbain.relisten.ui.home.LatestRecordingsListEntry
import com.stephenbain.relisten.com.stephenbain.relisten.ui.home.HomeHeader
import com.stephenbain.relisten.domain.HomeItem
import kotlin.time.ExperimentalTime

@ExperimentalFoundationApi
@ExperimentalTime
@ExperimentalMaterialApi
@ExperimentalStdlibApi
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    LoadingErrorListWithSeparators(
        state = viewModel.state.collectAsState().value.listState,
        error = { HomeError(viewModel::loadData) },
        headerContent = { HomeHeader(it) },
        itemContent = { HomeItem(it) },
    )
}

@ExperimentalMaterialApi
@Composable
fun HomeItem(item: HomeItem) = when (item) {
    is HomeItem.ArtistItem -> ArtistListEntry(item)
    is HomeItem.LatestRecordings -> LatestRecordingsListEntry(item)
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

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun HomeListItems(
    items: Map<HomeSeparator, List<HomeItem>>,
) = StickyHeaderList(
    map = items ,
    headerContent = { HomeHeader(item = it) },
    itemContent = { HomeItem(it) },
    key = HomeItem::key
)

val HomeItem.key: String
    get() = when (this) {
        is HomeItem.ArtistItem -> "artist=$name featured=$featured"
        is HomeItem.LatestRecordings -> "latest_recordings_carousel"
    }