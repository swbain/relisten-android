package com.stephenbain.relisten.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stephenbain.relisten.common.ui.BaseViewModel
import com.stephenbain.relisten.home.domain.GetHomeSections
import com.stephenbain.relisten.home.domain.model.HomeSection
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import timber.log.Timber

class HomeViewModel @Inject constructor(
    private val getHomeSections: GetHomeSections,
    private val backgroundScheduler: Scheduler
) : BaseViewModel() {

    private val _state = MutableLiveData<HomeState>()

    val state: LiveData<HomeState>
        get() = _state

    init {
        fetchData()
    }

    private fun fetchData() {
        autoDispose {
            getHomeItems().doOnError { Timber.e(it) }
                .map<HomeState> { HomeState.Success(it) }
                .onErrorReturn { HomeState.Error(it) }
                .startWith(HomeState.Loading)
                .subscribeOn(backgroundScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_state::postValue)
        }
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
            is HomeSection.RecentShows -> section.toHomeItems()
        }
    }

    private fun HomeSection.FeaturedArtists.toHomeItems(): List<HomeItem> {
        return artists.map { HomeItem.ArtistItem(it) }.toMutableList<HomeItem>().apply {
            add(0, HomeItem.Divider(HomeTitle.Featured))
        }
    }

    private fun HomeSection.AllArtists.toHomeItems(): List<HomeItem> {
        return artists.map { HomeItem.ArtistItem(it) }.toMutableList<HomeItem>().apply {
            add(0, HomeItem.Divider(HomeTitle.AllArtists(artists.size)))
        }
    }

    private fun HomeSection.RecentShows.toHomeItems(): List<HomeItem> {
        return listOf(HomeItem.Divider(HomeTitle.LatestRecordings), HomeItem.ShowsItem(shows))
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
