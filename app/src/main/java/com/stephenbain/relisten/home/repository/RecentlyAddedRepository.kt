package com.stephenbain.relisten.home.repository

import com.stephenbain.relisten.common.Show
import com.stephenbain.relisten.common.api.RelistenApi
import io.reactivex.Observable
import javax.inject.Inject
import timber.log.Timber

class RecentlyAddedRepository @Inject constructor(private val api: RelistenApi) {
    fun getRecentlyAddedShows(): Observable<List<Show>> {
        return api.getRecentlyAddedShows().toObservable()
            .doOnError { Timber.e(it, "Error getting shows from API") }
            .onErrorReturnItem(emptyList())
    }
}
