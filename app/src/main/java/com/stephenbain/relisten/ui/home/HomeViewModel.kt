package com.stephenbain.relisten.ui.home

import androidx.lifecycle.*
import com.stephenbain.relisten.data.domain.GetArtists
import com.stephenbain.relisten.data.domain.model.Artist
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


class HomeViewModel @Inject constructor(getArtists: GetArtists) : ViewModel() {

    val state: LiveData<HomeState> = getArtists().toFlowable()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError { Timber.e(it) }
        .map { HomeState.Success(it) as HomeState }
        .startWith(HomeState.Loading)
        .onErrorReturn { HomeState.Error(it) }
        .toLiveData()

    sealed class HomeState {
        object Loading : HomeState()
        data class Error(val t: Throwable) : HomeState()
        data class Success(val artists: List<Artist>) : HomeState()

        override fun toString(): String {
            return when (this) {
                is Error -> t.message ?: "unknown error"
                is Success -> artists.toString()
                is Loading -> "loading"
            }
        }
    }
}