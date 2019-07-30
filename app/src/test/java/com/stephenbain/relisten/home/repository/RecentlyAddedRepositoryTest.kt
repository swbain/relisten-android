package com.stephenbain.relisten.home.repository

import com.stephenbain.relisten.common.Artist
import com.stephenbain.relisten.common.Show
import com.stephenbain.relisten.common.api.RelistenApi
import com.stephenbain.relisten.common.db.RecentlyAddedDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class RecentlyAddedRepositoryTest {

    @MockK
    private lateinit var dao: RecentlyAddedDao

    @MockK
    private lateinit var api: RelistenApi

    private lateinit var repository: RecentlyAddedRepository

    init {
        MockKAnnotations.init(this)
    }

    @Before
    fun setup() {
        repository = RecentlyAddedRepository(api, dao)
    }

    @Test
    private fun getRecentlyAddedShows_apiError() {
        every { dao.getRecentlyAddedShows() } returns Observable.empty()
        every { api.getRecentlyAddedShows() } returns Single.error(Throwable("404"))
        val observer = repository.getRecentlyAddedShows().test()
        verify(inverse = true) { dao.putRecentlyAddedShows(any()) }
        observer.dispose()
    }

    @Test
    private fun getRecentlyAddedShows_emptyApiResponse() {
        every { dao.getRecentlyAddedShows() } returns Observable.empty()
        every { api.getRecentlyAddedShows() } returns Single.just(emptyList())
        val observer = repository.getRecentlyAddedShows().test()
        verify(inverse = true) { dao.putRecentlyAddedShows(any()) }
        observer.dispose()
    }

    @Test
    private fun getRecentlyAddedShows_validApiResponse() {
        val shows = listOf(Show(id = 0, artist = Artist(id = 0, name = "artist", isFeatured = false), displayDate = "date"))
        every { dao.getRecentlyAddedShows() } returns Observable.empty()
        every { api.getRecentlyAddedShows() } returns Single.just(shows)
        val observer = repository.getRecentlyAddedShows().test()
        verify { dao.putRecentlyAddedShows(shows) }
        observer.dispose()
    }
}
