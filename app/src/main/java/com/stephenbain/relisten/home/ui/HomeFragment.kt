package com.stephenbain.relisten.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stephenbain.relisten.R
import com.stephenbain.relisten.common.ui.BaseFragment
import com.stephenbain.relisten.common.ui.widget.BaseListAdapter
import com.stephenbain.relisten.common.ui.widget.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.home_item_artist.*
import kotlinx.android.synthetic.main.home_item_divider.*
import kotlinx.android.synthetic.main.home_item_shows.*

class HomeFragment : BaseFragment() {

    private val viewModel by lazy { getViewModel<HomeViewModel>() }
    private val adapter by lazy { HomeAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artistsList.layoutManager = LinearLayoutManager(activity)
        artistsList.adapter = adapter

        viewModel.state.observe(this, Observer { handleState(it) })
        viewModel.fetchData()
    }

    private fun handleState(state: HomeViewModel.HomeState) = when (state) {
        HomeViewModel.HomeState.Loading -> showLoading()
        is HomeViewModel.HomeState.Error -> showError()
        is HomeViewModel.HomeState.Success -> showSuccess(state.items)
    }

    private fun showLoading() {
        loading.isVisible = true
        artistsList.isVisible = false
        errorText.isVisible = false
    }

    private fun showError() {
        errorText.isVisible = true
        artistsList.isVisible = false
        loading.isVisible = false
    }

    private fun showSuccess(items: List<HomeItem>) {
        artistsList.isVisible = true
        errorText.isVisible = false
        loading.isVisible = false

        adapter.submitList(items)
    }

    private class HomeAdapter : BaseListAdapter<HomeItem, HomeItemViewHolder>(::sameItems, ::sameContents) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                when (viewType) {
                    VIEW_TYPE_DIVIDER -> R.layout.home_item_divider
                    VIEW_TYPE_ARTIST -> R.layout.home_item_artist
                    VIEW_TYPE_SHOWS -> R.layout.home_item_shows
                    else -> throw IllegalArgumentException("Unsupported viewType $viewType")
                },
                parent,
                false
            )
            return HomeItemViewHolder(view)
        }

        override fun getItemViewType(position: Int): Int {
            return when (getItem(position)) {
                is HomeItem.Divider -> VIEW_TYPE_DIVIDER
                is HomeItem.ArtistItem -> VIEW_TYPE_ARTIST
                is HomeItem.ShowsItem -> VIEW_TYPE_SHOWS
            }
        }

        companion object {

            private fun sameItems(oldItem: HomeItem, newItem: HomeItem): Boolean {
                return when {
                    oldItem is HomeItem.Divider && newItem is HomeItem.Divider -> {
                        oldItem.title == newItem.title
                    }
                    oldItem is HomeItem.ArtistItem && newItem is HomeItem.ArtistItem -> {
                        oldItem.artist.id == newItem.artist.id
                    }
                    oldItem is HomeItem.ShowsItem && newItem is HomeItem.ShowsItem -> true
                    else -> false
                }
            }

            private fun sameContents(oldItem: HomeItem, newItem: HomeItem): Boolean {
                return when {
                    oldItem is HomeItem.Divider && newItem is HomeItem.Divider -> {
                        oldItem.title.toString() == newItem.title.toString()
                    }
                    oldItem is HomeItem.ArtistItem && newItem is HomeItem.ArtistItem -> {
                        oldItem.artist == newItem.artist
                    }
                    oldItem is HomeItem.ShowsItem && newItem is HomeItem.ShowsItem -> {
                        oldItem.shows == newItem.shows
                    }
                    else -> false
                }
            }

            private const val VIEW_TYPE_DIVIDER = 0
            private const val VIEW_TYPE_ARTIST = 1
            private const val VIEW_TYPE_SHOWS = 2
        }
    }

    private class HomeItemViewHolder(override val containerView: View) : BaseViewHolder<HomeItem>(containerView) {

        override fun bind(item: HomeItem) = when (item) {
            is HomeItem.Divider -> bind(item)
            is HomeItem.ArtistItem -> bind(item)
            is HomeItem.ShowsItem -> bind(item)
        }

        private fun bind(item: HomeItem.Divider) {
            val resources = containerView.resources
            dividerTitle.text = when (item.title) {
                HomeTitle.RecentlyPlayed -> resources.getString(R.string.recently_played)
                HomeTitle.Featured -> resources.getString(R.string.featured)
                HomeTitle.LatestRecordings -> resources.getString(R.string.latest_recordings)
                is HomeTitle.AllArtists -> resources.getString(R.string.all_artists, item.title.count)
            }
        }

        private fun bind(item: HomeItem.ArtistItem) {
            artistName.text = item.artist.name
        }

        private fun bind(item: HomeItem.ShowsItem) {
            showsRecycler.setShows(item.shows)
        }
    }
}
