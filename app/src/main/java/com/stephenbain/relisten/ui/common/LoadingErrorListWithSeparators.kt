package com.stephenbain.relisten.com.stephenbain.relisten.ui.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable

@ExperimentalFoundationApi
@Composable
fun <Header, Item> LoadingErrorListWithSeparators(
    state: ContentState<Map<Header, List<Item>>>,
    loading: @Composable () -> Unit = { DefaultLoading() },
    error: @Composable () -> Unit,
    headerContent: @Composable LazyItemScope.(Header) -> Unit,
    itemContent: @Composable LazyItemScope.(Item) -> Unit,
    key: ((item: Item) -> Any)? = null,
) {
    LoadingErrorScreen(state, loading, error) {
        StickyHeaderList(it, headerContent, itemContent, key)
    }
}