package com.stephenbain.relisten.com.stephenbain.relisten.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

@Composable
fun <T> LoadingErrorList(
    state: ListState<T>,
    key: ((item: T) -> Any)? = null,
    loading: @Composable () -> Unit = { DefaultLoading() },
    error: @Composable () -> Unit,
    itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) = when (state) {
    is ListState.Error -> error()
    is ListState.Items<T> -> ListItems(state.items, key, itemContent)
    ListState.Loading -> loading()
}

@Composable
fun <T> ListItems(
    items: List<T>,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) = LazyColumn {
    items(items, key, itemContent)
}

@Composable
@Preview(showBackground = true)
fun DefaultLoading() = Box(
    modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
    contentAlignment = Alignment.Center
) {
    CircularProgressIndicator()
}

sealed class ListState<out T> {
    object Loading : ListState<Nothing>()
    data class Error(val t: Throwable) : ListState<Nothing>()
    data class Items<out T>(val items: List<T>) : ListState<T>()
}

fun <T> Flow<List<T>>.toListState(errorLogMessage: String? = null): Flow<ListState<T>> {
    return map<List<T>, ListState<T>> { ListState.Items(it) }
        .onStart { emit(ListState.Loading) }
        .catch {
            Timber.e(it, errorLogMessage)
            emit(ListState.Error(it))
        }
}