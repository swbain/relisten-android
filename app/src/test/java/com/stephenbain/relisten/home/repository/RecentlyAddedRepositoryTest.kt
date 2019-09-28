package com.stephenbain.relisten.home.repository

import com.stephenbain.relisten.common.Artist
import com.stephenbain.relisten.common.Show
import com.stephenbain.relisten.common.api.RelistenApi
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class RecentlyAddedRepositoryTest {

    @MockK
    private lateinit var api: RelistenApi

    private lateinit var repository: RecentlyAddedRepository

    init {
        MockKAnnotations.init(this)
    }

    @Before
    fun setup() {
        repository = RecentlyAddedRepository(api)
    }

    @Test
    fun getRecentlyAddedShows_apiError() {
        every { api.getRecentlyAddedShows() } returns Single.error(Throwable("404"))
        val observer = repository.getRecentlyAddedShows().test()
        observer.assertValues(emptyList())
        observer.dispose()
    }

    @Test
    fun getRecentlyAddedShows_emptyApiResponse() {
        every { api.getRecentlyAddedShows() } returns Single.just(emptyList())
        val observer = repository.getRecentlyAddedShows().test()
        observer.assertValues(emptyList())
        observer.dispose()
    }

    @Test
    fun getRecentlyAddedShows_validApiResponse() {
        val shows = listOf(Show(id = 0, artist = Artist(id = 0, name = "artist", isFeatured = false), displayDate = "date"))
        every { api.getRecentlyAddedShows() } returns Single.just(shows)
        val observer = repository.getRecentlyAddedShows().test()
        observer.assertValue(shows)
        observer.dispose()
    }
}
