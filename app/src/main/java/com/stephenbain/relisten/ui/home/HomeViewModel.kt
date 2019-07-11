package com.stephenbain.relisten.ui.home

import androidx.lifecycle.*
import com.stephenbain.relisten.domain.GetHomeSections
import com.stephenbain.relisten.domain.model.HomeSection
import com.stephenbain.relisten.repository.Artist
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.concatAll
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


class HomeViewModel @Inject constructor(private val getHomeSections: GetHomeSections) : ViewModel() {

    val state: LiveData<HomeState> = getHomeItems().toFlowable(BackpressureStrategy.LATEST)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError { Timber.e(it) }
        .map<HomeState> { HomeState.Success(it) }
        .startWith(HomeState.Loading)
        .onErrorReturn { HomeState.Error(it) }
        .toLiveData()

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
        return mutableListOf<HomeItem>(HomeItem.Divider(HomeTitle.FEATURED)).apply {
            addAll(artists.map { HomeItem.ArtistItem(it) })
        }
    }

    private fun HomeSection.AllArtists.toHomeItems(): List<HomeItem> {
        return mutableListOf<HomeItem>(HomeItem.Divider(HomeTitle.ALL_ARTISTS)).apply {
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