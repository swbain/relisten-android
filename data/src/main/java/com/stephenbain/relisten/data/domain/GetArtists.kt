package com.stephenbain.relisten.data.domain

import com.stephenbain.relisten.data.api.RelistenApi
import com.stephenbain.relisten.data.domain.model.Artist
import com.stephenbain.relisten.data.domain.model.toArtist
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