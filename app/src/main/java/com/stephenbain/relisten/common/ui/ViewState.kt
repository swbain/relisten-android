package com.stephenbain.relisten.common.ui


sealed class ViewState<T> {
    class Loading<T> : ViewState<T>()
    data class Error<T>(val t: Throwable) : ViewState<T>()
    data class Success<T>(val payload: T) : ViewState<T>()
}