package com.stephenbain.relisten.home.domain

import com.stephenbain.relisten.home.domain.model.HomeSection
import com.stephenbain.relisten.home.repository.Artist
import com.stephenbain.relisten.home.repository.ArtistRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.toObservable
import timber.log.Timber
import javax.inject.Inject


class GetHomeSections @Inject constructor(private val artistRepository: ArtistRepository) {

    operator fun invoke(): Observable<List<HomeSection>> {
        return artistRepository.getArtists()
            .doOnError { Timber.e(it, "Error getting all artists from repo") }
            .onErrorReturnItem(emptyList())
            .map(::getHomeSections)
    }

    private fun getHomeSections(artists: List<Artist>): List<HomeSection> {
        return listOf(getFeaturedArtists(artists), getAllArtists(artists))
    }

    private fun getRecentlyPlayed(): Observable<HomeSection> {
        // TODO: load from database
        return Observable.empty()
    }

    private fun getFeaturedArtists(artists: List<Artist>): HomeSection {
        return HomeSection.FeaturedArtists(artists.filter { it.isFeatured })
    }

    private fun getLatestRecordings(): Observable<HomeSection> {
        // TODO: load recordings from API
        return Observable.empty()
    }

    private fun getAllArtists(artists: List<Artist>): HomeSection {
        return HomeSection.AllArtists(artists)
    }

}