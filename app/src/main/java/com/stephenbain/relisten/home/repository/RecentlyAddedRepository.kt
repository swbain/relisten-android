package com.stephenbain.relisten.home.repository

import com.stephenbain.relisten.common.Show
import com.stephenbain.relisten.common.api.RelistenApi
import com.stephenbain.relisten.common.db.RecentlyAddedDao
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import timber.log.Timber

class RecentlyAddedRepository @Inject constructor(private val api: RelistenApi, private val dao: RecentlyAddedDao) {

    fun getRecentlyAddedShows(): Observable<List<Show>> {
        return fetchAndSave()
            .startWith(getFirstResponseFromDb())
            .concatWith(dao.getRecentlyAddedShows())
            .distinct()
    }

    private fun getFirstResponseFromDb(): Observable<List<Show>> {
        return dao.getRecentlyAddedShows()
            .firstElement()
            .filter { it.isNotEmpty() }
            .toObservable()
    }

    private fun fetchAndSave(): Completable {
        return api.getRecentlyAddedShows()
            .doOnError { Timber.e(it, "Error requesting recently added shows from api") }
            .onErrorReturnItem(emptyList())
            .flatMapCompletable { if (it.isNotEmpty()) dao.putRecentlyAddedShows(it) else Completable.complete() }
    }
}
