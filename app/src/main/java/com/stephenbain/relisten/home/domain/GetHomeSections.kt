package com.stephenbain.relisten.home.domain

import com.stephenbain.relisten.common.Artist
import com.stephenbain.relisten.home.domain.model.HomeSection
import com.stephenbain.relisten.home.repository.ArtistsRepository
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject


class GetHomeSections @Inject constructor(private val artistsRepository: ArtistsRepository) {

    operator fun invoke(): Observable<List<HomeSection>> {
        return artistsRepository.getArtists()
            .doOnError { Timber.e(it, "Error getting all artists from repo") }
            .onErrorReturnItem(emptyList())
            .map(::getHomeSections)
    }

    private fun getHomeSections(artists: List<Artist>): List<HomeSection> {
        return listOf(getFeaturedArtists(artists), getAllArtists(artists)).filter {
            when (it) {
                is HomeSection.FeaturedArtists -> it.artists.isNotEmpty()
                is HomeSection.AllArtists -> it.artists.isNotEmpty()
            }
        }
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