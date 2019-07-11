package com.stephenbain.relisten.home.domain

import com.stephenbain.relisten.home.domain.model.HomeSection
import com.stephenbain.relisten.home.repository.Artist
import com.stephenbain.relisten.home.repository.ArtistRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.toObservable
import javax.inject.Inject


class GetHomeSections @Inject constructor(private val artistRepository: ArtistRepository) {

    operator fun invoke(): Observable<List<HomeSection>> {
        return artistRepository.getArtists().flatMap(::getHomeSections)
    }

    private fun getHomeSections(artists: List<Artist>): Observable<List<HomeSection>> {
        return Observables.combineLatest(
            getFeaturedArtists(artists),
            getAllArtists(artists)
        ).map { listOf(it.first, it.second) }
    }

    private fun getRecentlyPlayed(): Observable<HomeSection> {
        // TODO: load from database
        return Observable.empty()
    }

    private fun getFeaturedArtists(artists: List<Artist>): Observable<out HomeSection> {
        return artists.toObservable()
            .filter { it.isFeatured }
            .toList()
            .map { HomeSection.FeaturedArtists(it) }
            .toObservable()
    }

    private fun getLatestRecordings(): Observable<HomeSection> {
        // TODO: load recordings from API
        return Observable.empty()
    }

    private fun getAllArtists(artists: List<Artist>): Observable<out HomeSection> {
        return artists.toObservable()
            .toList()
            .map { HomeSection.AllArtists(it) }
            .toObservable()
    }

}