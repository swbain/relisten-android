package com.stephenbain.relisten.home.domain

import com.stephenbain.relisten.common.Artist
import com.stephenbain.relisten.home.domain.model.HomeSection
import com.stephenbain.relisten.home.repository.ArtistsRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class GetHomeSectionsTest {

    @MockK
    lateinit var artistsRepo: ArtistsRepository

    lateinit var getHomeSections: GetHomeSections

    init {
        MockKAnnotations.init(this)
    }

    @Before
    fun setup() {
        getHomeSections = GetHomeSections(artistsRepo)
    }

    @Test
    fun invoke_errorGettingArtists() {
        every { artistsRepo.getArtists() } returns Observable.error(Throwable("something bad happened!"))
        val observer = getHomeSections().test()
        observer.assertValue(emptyList())
        observer.dispose()
    }

    @Test
    fun invoke_noFeaturedArtists() {
        val artists = listOf(Artist(id = 1, name = "artist 1", isFeatured = false))
        every { artistsRepo.getArtists() } returns Observable.just(artists)
        val observer = getHomeSections().test()
        observer.assertValue(listOf(HomeSection.AllArtists(artists)))
        observer.dispose()
    }

    @Test
    fun invoke_hasFeaturedArtists() {
        val artist1 = Artist(id = 1, name = "artist 1", isFeatured = false)
        val artist2 = Artist(id = 1, name = "artist 2", isFeatured = true)
        every { artistsRepo.getArtists() } returns Observable.just(listOf(artist1, artist2))
        val observer = getHomeSections().test()
        observer.assertValue(listOf(
            HomeSection.FeaturedArtists(listOf(artist2)),
            HomeSection.AllArtists(listOf(artist1, artist2))
        ))
        observer.dispose()
    }
}
