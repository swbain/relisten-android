package com.stephenbain.relisten.domain

import com.stephenbain.relisten.api.RelistenApi
import com.stephenbain.relisten.domain.model.Artist
import com.stephenbain.relisten.domain.model.toArtist
import io.reactivex.Single
import io.reactivex.rxkotlin.flatMapIterable
import javax.inject.Inject


class GetArtists @Inject internal constructor(private val relistenApi: RelistenApi) {

    operator fun invoke(): Single<List<Artist>> {
        return relistenApi.getArtists()
            .toObservable()
            .flatMapIterable()
            .map { it.toArtist() }
            .toList()
    }

}