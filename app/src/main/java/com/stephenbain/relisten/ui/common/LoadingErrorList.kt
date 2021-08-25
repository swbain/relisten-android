package com.stephenbain.relisten.com.stephenbain.relisten.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
    state: ContentState<T>,
    loading: @Composable () -> Unit = { DefaultLoading() },
    error: @Composable () -> Unit,
    success: @Composable (T) -> Unit,
) = when (state) {
    is ContentState.Error -> error()
    is ContentState.Success<T> -> success(state.successState)
    ContentState.Loading -> loading()
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

sealed class ContentState<out T> {
    object Loading : ContentState<Nothing>()
    data class Error(val t: Throwable) : ContentState<Nothing>()
    data class Success<out T>(val successState: T) : ContentState<T>()
}

fun <T> Flow<T>.toContentState(errorLogMessage: String? = null): Flow<ContentState<T>> {
    return map<T, ContentState<T>> { ContentState.Success(it) }
        .onStart { emit(ContentState.Loading) }
        .catch {
            Timber.e(it, errorLogMessage)
            emit(ContentState.Error(it))
        }
}