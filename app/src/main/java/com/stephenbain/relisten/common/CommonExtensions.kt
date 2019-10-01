package com.stephenbain.relisten.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, crossinline observer: (T) -> Unit) {
    observe(lifecycleOwner, Observer { observer(it) })
}

val <T> T.exhaustive: T
    get() = this