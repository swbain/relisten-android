package com.stephenbain.relisten.common.ui

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BaseViewModel : ViewModel() {

    private val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    protected fun autoDispose(toDispose: () -> Disposable) {
        disposable.add(toDispose())
    }


}