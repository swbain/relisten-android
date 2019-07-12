package com.stephenbain.relisten.home.domain

import com.stephenbain.relisten.home.domain.model.HomeSection
import com.stephenbain.relisten.home.repository.Artist
import com.stephenbain.relisten.home.repository.ArtistRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test


class GetHomeSectionsTest {

    @MockK
    lateinit var artistRepo: ArtistRepository

    lateinit var getHomeSections: GetHomeSections

    init {
        MockKAnnotations.init(this)
    }

    @Before
    fun setup() {
        getHomeSections = GetHomeSections(artistRepo)
    }

    @Test
    fun invoke_errorGettingArtists() {
        every { artistRepo.getArtists() } returns Observable.error(Throwable("404"))
        val observer = getHomeSections().test()
        observer.assertValue(emptyList())
        observer.dispose()
    }

    @Test
    fun invoke_noFeaturedArtists() {
        val artists = listOf(
            Artist(id = 1, name = "artist 1", isFavorite = false, isFeatured = false)
        )
        every { artistRepo.getArtists() } returns Observable.just(artists)
        val observer = getHomeSections().test()
        observer.assertValue(listOf(HomeSection.AllArtists(artists)))
        observer.dispose()
    }

    @Test
    fun invoke_hasFeaturedArtists() {
        val artist1 = Artist(id = 1, name = "artist 1", isFavorite = false, isFeatured = false)
        val artist2 = Artist(id = 1, name = "artist 2", isFavorite = false, isFeatured = true)
        every { artistRepo.getArtists() } returns Observable.just(listOf(artist1, artist2))
        val observer = getHomeSections().test()
        observer.assertValue(listOf(
            HomeSection.FeaturedArtists(listOf(artist2)),
            HomeSection.AllArtists(listOf(artist1, artist2))
        ))
        observer.dispose()
    }

}