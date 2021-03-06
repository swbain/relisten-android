package com.stephenbain.relisten.home.repository

import com.stephenbain.relisten.common.Artist
import com.stephenbain.relisten.common.api.RelistenApi
import io.reactivex.Observable
import javax.inject.Inject
import timber.log.Timber

class ArtistsRepository @Inject constructor(private val relistenApi: RelistenApi) {

    fun getArtists(): Observable<List<Artist>> {
        return relistenApi.getArtists().toObservable()
            .doOnError { Timber.e(it, "Error getting artists from API") }
            .onErrorReturnItem(emptyList())
    }
}
