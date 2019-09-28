package com.stephenbain.relisten.home.repository

import com.stephenbain.relisten.common.Show
import com.stephenbain.relisten.common.api.RelistenApi
import com.stephenbain.relisten.common.db.RecentlyAddedDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import timber.log.Timber

class RecentlyAddedRepository @Inject constructor(
    private val api: RelistenApi,
    private val dao: RecentlyAddedDao
) {

    fun getRecentlyAddedShows(): Observable<List<Show>> {
        return getFirstResponseFromDb().flatMapObservable { shows ->
            fetchAndSave(shows).startWith(Observable.just(shows))
                .concatWith(dao.getRecentlyAddedShows())
                .distinct()
        }
    }

    private fun getFirstResponseFromDb(): Single<List<Show>> {
        return dao.getRecentlyAddedShows().first(emptyList())
    }

    private fun fetchAndSave(savedShows: List<Show>): Completable {
        return api.getRecentlyAddedShows()
            .doOnError { Timber.e(it, "Error requesting recently added shows from api") }
            .onErrorReturnItem(emptyList())
            .flatMapCompletable {
                saveAndClearShows(it, savedShows)
            }
    }

    private fun saveAndClearShows(newShows: List<Show>, savedShows: List<Show>): Completable {
        return if (savedShows.isEmpty() && newShows.isNotEmpty()) {
            dao.putRecentlyAddedShows(newShows)
        } else if (newShows.isNotEmpty() && newShows != savedShows) {
            dao.clearRecentlyAddedShows().andThen(dao.putRecentlyAddedShows(newShows))
        } else Completable.complete()
    }
}
