package com.stephenbain.relisten.home.domain

import com.stephenbain.relisten.common.Artist
import com.stephenbain.relisten.common.Show
import com.stephenbain.relisten.home.domain.model.HomeSection
import com.stephenbain.relisten.home.repository.ArtistsRepository
import com.stephenbain.relisten.home.repository.RecentlyAddedRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class GetHomeSectionsTest {

    @MockK
    lateinit var artistsRepo: ArtistsRepository

    @MockK
    lateinit var recentlyAddedRepo: RecentlyAddedRepository

    lateinit var getHomeSections: GetHomeSections

    init {
        MockKAnnotations.init(this)
    }

    @Before
    fun setup() {
        getHomeSections = GetHomeSections(artistsRepo, recentlyAddedRepo)
    }

    @Test
    fun invoke_errorGettingArtists_errorGettingShows() {
        every { artistsRepo.getArtists() } returns Observable.error(Throwable("something bad happened!"))
        every { recentlyAddedRepo.getRecentlyAddedShows() } returns Observable.error(Throwable("another bad thing happened!"))
        val observer = getHomeSections().test()
        observer.assertValue(emptyList())
        observer.dispose()
    }

    @Test
    fun invoke_errorGettingArtists_noShows() {
        every { artistsRepo.getArtists() } returns Observable.error(Throwable("something bad happened!"))
        every { recentlyAddedRepo.getRecentlyAddedShows() } returns Observable.just(emptyList())
        val observer = getHomeSections().test()
        observer.assertValue(emptyList())
        observer.dispose()
    }

    @Test
    fun invoke_errorGettingArtists_hasShows() {
        val shows = listOf(Show(id = 0, date = "", artist = Artist(id = 0, name = "artist", isFeatured = false)))
        every { artistsRepo.getArtists() } returns Observable.error(Throwable("something bad happened!"))
        every { recentlyAddedRepo.getRecentlyAddedShows() } returns Observable.just(shows)
        val observer = getHomeSections().test()
        observer.assertValue(listOf(HomeSection.RecentShows(shows)))
        observer.dispose()
    }

    @Test
    fun invoke_noFeaturedArtists_noShows() {
        val artists = listOf(Artist(id = 1, name = "artist 1", isFeatured = false))
        every { artistsRepo.getArtists() } returns Observable.just(artists)
        every { recentlyAddedRepo.getRecentlyAddedShows() } returns Observable.just(emptyList())
        val observer = getHomeSections().test()
        observer.assertValue(listOf(HomeSection.AllArtists(artists)))
        observer.dispose()
    }

    @Test
    fun invoke_noFeaturedArtists_errorGettingShows() {
        val artists = listOf(Artist(id = 1, name = "artist 1", isFeatured = false))
        every { artistsRepo.getArtists() } returns Observable.just(artists)
        every { recentlyAddedRepo.getRecentlyAddedShows() } returns Observable.error(Throwable("something bad happened!"))
        val observer = getHomeSections().test()
        observer.assertValue(listOf(HomeSection.AllArtists(artists)))
        observer.dispose()
    }

    @Test
    fun invoke_noFeaturedArtists_hasShow() {
        val artist = Artist(id = 1, name = "artist 1", isFeatured = false)
        val artists = listOf(artist)
        val shows = listOf(Show(id = 0, date = "date", artist = artist))
        every { artistsRepo.getArtists() } returns Observable.just(artists)
        every { recentlyAddedRepo.getRecentlyAddedShows() } returns Observable.just(shows)
        val observer = getHomeSections().test()
        observer.assertValue(listOf(HomeSection.RecentShows(shows), HomeSection.AllArtists(artists)))
        observer.dispose()
    }

    @Test
    fun invoke_hasFeaturedArtists_errorGettingShow() {
        val artist1 = Artist(id = 1, name = "artist 1", isFeatured = false)
        val artist2 = Artist(id = 1, name = "artist 2", isFeatured = true)
        every { artistsRepo.getArtists() } returns Observable.just(listOf(artist1, artist2))
        every { recentlyAddedRepo.getRecentlyAddedShows() } returns Observable.error(Throwable("bad error!!!"))
        val observer = getHomeSections().test()
        observer.assertValue(listOf(
            HomeSection.FeaturedArtists(listOf(artist2)),
            HomeSection.AllArtists(listOf(artist1, artist2))
        ))
        observer.dispose()
    }

    @Test
    fun invoke_hasFeaturedArtists_noShows() {
        val artist1 = Artist(id = 1, name = "artist 1", isFeatured = false)
        val artist2 = Artist(id = 1, name = "artist 2", isFeatured = true)
        every { artistsRepo.getArtists() } returns Observable.just(listOf(artist1, artist2))
        every { recentlyAddedRepo.getRecentlyAddedShows() } returns Observable.just(emptyList())
        val observer = getHomeSections().test()
        observer.assertValue(listOf(
            HomeSection.FeaturedArtists(listOf(artist2)),
            HomeSection.AllArtists(listOf(artist1, artist2))
        ))
        observer.dispose()
    }

    @Test
    fun invoke_hasFeaturedArtists_hasShows() {
        val artist1 = Artist(id = 1, name = "artist 1", isFeatured = false)
        val artist2 = Artist(id = 1, name = "artist 2", isFeatured = true)
        val shows = listOf(Show(id = 1, date = "date", artist = artist1))
        every { artistsRepo.getArtists() } returns Observable.just(listOf(artist1, artist2))
        every { recentlyAddedRepo.getRecentlyAddedShows() } returns Observable.just(shows)
        val observer = getHomeSections().test()
        observer.assertValue(listOf(
            HomeSection.FeaturedArtists(listOf(artist2)),
            HomeSection.RecentShows(shows),
            HomeSection.AllArtists(listOf(artist1, artist2))
        ))
        observer.dispose()
    }
}
