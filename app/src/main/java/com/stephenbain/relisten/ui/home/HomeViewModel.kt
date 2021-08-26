package com.stephenbain.relisten.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephenbain.relisten.com.stephenbain.relisten.ui.common.ContentState
import com.stephenbain.relisten.com.stephenbain.relisten.ui.common.toContentState
import com.stephenbain.relisten.domain.GetHomeItems
import com.stephenbain.relisten.domain.HomeData
import com.stephenbain.relisten.domain.HomeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalStdlibApi
@HiltViewModel
class HomeViewModel @Inject constructor(private val getHomeItems: GetHomeItems) : ViewModel() {

    private val _state = MutableStateFlow(HomeState(ContentState.Loading))

    val state: StateFlow<HomeState>
        get() = _state

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            getHomeItems().map { it.toItemsMap() }
                .toContentState()
                .map { HomeState(it) }
                .collect(_state::value::set)
        }
    }

    private fun HomeData.toItemsMap(): Map<HomeSeparator, List<HomeItem>> = buildMap {
        if (featuredArtists.isNotEmpty()) {
            put(HomeSeparator.Featured, featuredArtists)
        }

        if (latestRecordings.recordings.isNotEmpty()) {
            put(HomeSeparator.LatestRecordings, listOf(latestRecordings))
        }

        if (allArtists.isNotEmpty()) {
            put(HomeSeparator.AllArtists(allArtists.size), allArtists)
        }
    }
}

data class HomeState(val listState: ContentState<Map<HomeSeparator, List<HomeItem>>>)

sealed class HomeSeparator {
    object Featured : HomeSeparator()
    object LatestRecordings : HomeSeparator()
    data class AllArtists(val count: Int) : HomeSeparator()
}