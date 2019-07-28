package com.stephenbain.relisten.home.repository

import com.stephenbain.relisten.common.Artist
import com.stephenbain.relisten.common.api.RelistenApi
import com.stephenbain.relisten.common.db.ArtistDao
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import timber.log.Timber

class ArtistsRepository @Inject constructor(private val relistenApi: RelistenApi, private val artistDao: ArtistDao) {

    fun getArtists(): Observable<List<Artist>> {
        return fetchAndSaveArtists()
            .startWith(getFirstResponseFromDb())
            .concatWith(artistDao.getAllArtists())
            .distinct()
    }

    private fun getFirstResponseFromDb(): Observable<List<Artist>> {
        return artistDao.getAllArtists()
            .firstElement()
            .filter { it.isNotEmpty() }
            .toObservable()
    }

    private fun fetchAndSaveArtists(): Completable {
        return relistenApi.getArtists()
            .doOnError { Timber.e(it, "Error requesting artists from api") }
            .onErrorReturnItem(emptyList())
            .flatMapCompletable { if (it.isNotEmpty()) artistDao.putArtists(it) else Completable.complete() }
    }
}
