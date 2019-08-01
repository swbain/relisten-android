package com.stephenbain.relisten.home.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.stephenbain.relisten.common.Artist
import com.stephenbain.relisten.common.Show
import com.stephenbain.relisten.home.domain.GetHomeSections
import com.stephenbain.relisten.home.domain.model.HomeSection
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()

    @MockK lateinit var getHomeSections: GetHomeSections
    @MockK(relaxUnitFun = true) lateinit var observer: Observer<HomeViewModel.HomeState>

    lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = HomeViewModel(getHomeSections, Schedulers.trampoline())
        viewModel.state.observeForever(observer)
    }

    @Test
    fun error() {
        val throwable = Throwable("something bad happened")
        every { getHomeSections() } returns Observable.error(throwable)
        viewModel.fetchData()
        verify { observer.onChanged(HomeViewModel.HomeState.Loading) }
        verify { observer.onChanged(HomeViewModel.HomeState.Error(throwable)) }
    }

    @Test
    fun noHomeSections() {
        every { getHomeSections() } returns Observable.just(emptyList())
        viewModel.fetchData()
        verify { observer.onChanged(HomeViewModel.HomeState.Loading) }
        verify { observer.onChanged(HomeViewModel.HomeState.Success(emptyList())) }
    }

    @Test
    fun hasArtists_noFeaturedArtists_noRecentShows() {
        val artists = listOf(Artist(id = 1, name = "artist", isFeatured = false))
        val sections = listOf(HomeSection.AllArtists(artists))
        every { getHomeSections() } returns Observable.just(sections)
        viewModel.fetchData()

        val items = artists.map { HomeItem.ArtistItem(it) }.toMutableList<HomeItem>().apply {
            add(0, HomeItem.Divider(HomeTitle.AllArtists(artists.size)))
        }

        verify { observer.onChanged(HomeViewModel.HomeState.Loading) }
        verify { observer.onChanged(HomeViewModel.HomeState.Success(items)) }
    }

    @Test
    fun hasArtists_hasFeaturedArtists_noRecentShows() {
        val artist1 = Artist(id = 1, name = "artist 1", isFeatured = false)
        val artist2 = Artist(id = 2, name = "artist 2", isFeatured = true)
        val allArtists = listOf(artist1, artist2)
        val featuredArtists = listOf(artist2)
        val sections = listOf(
            HomeSection.FeaturedArtists(featuredArtists),
            HomeSection.AllArtists(allArtists)
        )

        every { getHomeSections() } returns Observable.just(sections)

        val expectedItems = mutableListOf<HomeItem>()
        expectedItems.add(HomeItem.Divider(HomeTitle.Featured))
        expectedItems.addAll(featuredArtists.map { HomeItem.ArtistItem(it) })
        expectedItems.add(HomeItem.Divider(HomeTitle.AllArtists(allArtists.size)))
        expectedItems.addAll(allArtists.map { HomeItem.ArtistItem(it) })

        viewModel.fetchData()

        verify { observer.onChanged(HomeViewModel.HomeState.Loading) }
        verify { observer.onChanged(HomeViewModel.HomeState.Success(expectedItems)) }
    }

    @Test
    fun hasArtists_hasFeaturedArtists_hasRecentShows() {
        val artist1 = Artist(id = 1, name = "artist 1", isFeatured = false)
        val artist2 = Artist(id = 2, name = "artist 2", isFeatured = true)
        val recentShow = Show(id = 1, displayDate = "date", artist = artist1)

        val allArtists = listOf(artist1, artist2)
        val featuredArtists = listOf(artist2)
        val recentShows = listOf(recentShow)

        val sections = listOf(
            HomeSection.FeaturedArtists(featuredArtists),
            HomeSection.RecentShows(recentShows),
            HomeSection.AllArtists(allArtists)
        )

        every { getHomeSections() } returns Observable.just(sections)

        val expectedItems = mutableListOf<HomeItem>()
        expectedItems.add(HomeItem.Divider(HomeTitle.Featured))
        expectedItems.addAll(featuredArtists.map { HomeItem.ArtistItem(it) })
        expectedItems.add(HomeItem.Divider(HomeTitle.LatestRecordings))
        expectedItems.add(HomeItem.ShowsItem(recentShows))
        expectedItems.add(HomeItem.Divider(HomeTitle.AllArtists(allArtists.size)))
        expectedItems.addAll(allArtists.map { HomeItem.ArtistItem(it) })

        viewModel.fetchData()

        verify { observer.onChanged(HomeViewModel.HomeState.Loading) }
        verify { observer.onChanged(HomeViewModel.HomeState.Success(expectedItems)) }
    }

    @Test
    fun noArtists_noFeaturedArtists_hasRecentShows() {
        val artist1 = Artist(id = 1, name = "artist 1", isFeatured = false)
        val recentShow = Show(id = 1, displayDate = "date", artist = artist1)

        val recentShows = listOf(recentShow)

        val sections = listOf(HomeSection.RecentShows(recentShows))

        every { getHomeSections() } returns Observable.just(sections)

        val expectedItems = mutableListOf<HomeItem>()
        expectedItems.add(HomeItem.Divider(HomeTitle.LatestRecordings))
        expectedItems.add(HomeItem.ShowsItem(recentShows))

        viewModel.fetchData()

        verify { observer.onChanged(HomeViewModel.HomeState.Loading) }
        verify { observer.onChanged(HomeViewModel.HomeState.Success(expectedItems)) }
    }
}
