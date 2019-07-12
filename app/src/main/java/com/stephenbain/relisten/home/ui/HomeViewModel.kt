package com.stephenbain.relisten.home.ui

import androidx.lifecycle.*
import com.stephenbain.relisten.home.domain.GetHomeSections
import com.stephenbain.relisten.home.domain.model.HomeSection
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


class HomeViewModel @Inject constructor(private val getHomeSections: GetHomeSections) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val _state = MutableLiveData<HomeState>()

    val state: LiveData<HomeState> = _state

    init {
        disposable.add(
            getHomeItems().doOnError { Timber.e(it) }
                .map<HomeState> { HomeState.Success(it) }
                .onErrorReturn { HomeState.Error(it) }
                .startWith(HomeState.Loading)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_state::postValue)
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private fun getHomeItems(): Observable<List<HomeItem>> {
        return getHomeSections().map { sections ->
            sections.map { sectionToHomeItems(it) }.flatten()
        }
    }

    private fun sectionToHomeItems(section: HomeSection): List<HomeItem> {
        return when (section) {
            is HomeSection.FeaturedArtists -> section.toHomeItems()
            is HomeSection.AllArtists -> section.toHomeItems()
        }
    }

    private fun HomeSection.FeaturedArtists.toHomeItems(): List<HomeItem> {
        return mutableListOf<HomeItem>(
            HomeItem.Divider(
                HomeTitle.FEATURED
            )
        ).apply {
            addAll(artists.map { HomeItem.ArtistItem(it) })
        }
    }

    private fun HomeSection.AllArtists.toHomeItems(): List<HomeItem> {
        return mutableListOf<HomeItem>(
            HomeItem.Divider(
                HomeTitle.ALL_ARTISTS
            )
        ).apply {
            addAll(artists.map { HomeItem.ArtistItem(it) })
        }
    }

    sealed class HomeState {
        object Loading : HomeState()
        data class Error(val t: Throwable) : HomeState()
        data class Success(val items: List<HomeItem>) : HomeState()

        override fun toString(): String {
            return when (this) {
                is Error -> t.message ?: "unknown error"
                is Success -> items.toString()
                is Loading -> "loading"
            }
        }
    }
}