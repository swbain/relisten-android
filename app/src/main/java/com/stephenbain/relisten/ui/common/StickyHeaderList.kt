package com.stephenbain.relisten.com.stephenbain.relisten.ui.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@ExperimentalFoundationApi
@Composable
fun <Header, Item> StickyHeaderList(
    map: Map<Header, List<Item>>,
    headerContent: @Composable LazyItemScope.(Header) -> Unit,
    itemContent: @Composable LazyItemScope.(Item) -> Unit,
    key: ((item: Item) -> Any)? = null,
) {
    LazyColumn {
        map.forEach { (header, items) ->
            stickyHeader { headerContent(header) }
            items(items, key, itemContent)
        }
    }
}