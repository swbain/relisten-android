package com.stephenbain.relisten.home.repository

import com.stephenbain.relisten.common.Artist
import com.stephenbain.relisten.common.api.RelistenApi
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class ArtistsRepositoryTest {

    @MockK
    private lateinit var relistenApi: RelistenApi

    private lateinit var artistsRepository: ArtistsRepository

    init {
        MockKAnnotations.init(this)
    }

    @Before
    fun setup() {
        artistsRepository = ArtistsRepository(relistenApi)
    }

    @Test
    fun getArtists_apiError() {
        every { relistenApi.getArtists() } returns Single.error { Throwable("404!") }
        val observer = artistsRepository.getArtists().test()
        observer.assertValue(emptyList())
        observer.dispose()
    }

    @Test
    fun getArtists_emptyApiResponse() {
        every { relistenApi.getArtists() } returns Single.just(emptyList())
        val observer = artistsRepository.getArtists().test()
        observer.assertValue(emptyList())
        observer.dispose()
    }

    @Test
    fun getArtists_validApiResponse() {
        val artists = listOf(Artist(id = 1, name = "artist", isFeatured = false))
        every { relistenApi.getArtists() } returns Single.just(artists)
        val observer = artistsRepository.getArtists().test()
        observer.assertValue(artists)
        observer.dispose()
    }
}
