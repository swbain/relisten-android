package com.stephenbain.relisten.home.domain

import com.stephenbain.relisten.common.Artist
import com.stephenbain.relisten.common.Show
import com.stephenbain.relisten.home.domain.model.HomeSection
import com.stephenbain.relisten.home.repository.ArtistsRepository
import com.stephenbain.relisten.home.repository.RecentlyAddedRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import javax.inject.Inject
import timber.log.Timber

class GetHomeSections @Inject constructor(
    private val artistsRepository: ArtistsRepository,
    private val recentlyAddedRepository: RecentlyAddedRepository
) {

    operator fun invoke(): Observable<List<HomeSection>> {
        return combineArtistsAndShows().map { getHomeSections(it.first, it.second) }.onErrorReturnItem(emptyList())
    }

    private fun combineArtistsAndShows(): Observable<Pair<List<Artist>, List<Show>>> {
        return Observables.combineLatest(getArtistsFromRepo(), getShowsFromRepo())
    }

    private fun getArtistsFromRepo(): Observable<List<Artist>> {
        return artistsRepository.getArtists()
            .doOnError { Timber.e(it, "Error getting all artists from repo") }
            .onErrorReturnItem(emptyList())
    }

    private fun getShowsFromRepo(): Observable<List<Show>> {
        return recentlyAddedRepository.getRecentlyAddedShows()
            .doOnError { Timber.e(it, "error getting recently added shows from repo") }
            .onErrorReturnItem(emptyList())
    }

    private fun getHomeSections(artists: List<Artist>, shows: List<Show>): List<HomeSection> {
        val sections = listOf(
            HomeSection.FeaturedArtists(artists.filter { it.isFeatured }),
            HomeSection.RecentShows(shows),
            HomeSection.AllArtists(artists)
        )

        return sections.filter {
            when (it) {
                is HomeSection.FeaturedArtists -> it.artists.isNotEmpty()
                is HomeSection.AllArtists -> it.artists.isNotEmpty()
                is HomeSection.RecentShows -> it.shows.isNotEmpty()
            }
        }
    }
}
