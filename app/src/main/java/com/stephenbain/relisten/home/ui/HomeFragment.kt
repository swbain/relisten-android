package com.stephenbain.relisten.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stephenbain.relisten.R
import com.stephenbain.relisten.common.ui.BaseFragment
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_home.*


class HomeFragment : BaseFragment() {

    private val viewModel by lazy { getViewModel<HomeViewModel>() }

    private val adapter by lazy { HomeAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(this, Observer { handleState(it) })

        artistsList.layoutManager = LinearLayoutManager(activity)
        artistsList.adapter = adapter
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

    private class HomeAdapter : ListAdapter<HomeItem, HomeItemViewHolder>(
        DIFF_CALLBACK
    ) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
            return HomeItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
            holder.bind(getItem(position))
        }

        companion object {
            private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HomeItem>() {
                override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
                    return when {
                        oldItem is HomeItem.Divider && newItem is HomeItem.Divider -> {
                            oldItem.title == newItem.title
                        }
                        oldItem is HomeItem.ArtistItem && newItem is HomeItem.ArtistItem -> {
                            oldItem.artist.id == newItem.artist.id
                        }
                        else -> false
                    }
                }

                override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
                    return when {
                        oldItem is HomeItem.Divider && newItem is HomeItem.Divider -> {
                            oldItem.title == newItem.title
                        }
                        oldItem is HomeItem.ArtistItem && newItem is HomeItem.ArtistItem -> {
                            oldItem.artist == newItem.artist
                        }
                        else -> false
                    }
                }
            }
        }

    }

    private class HomeItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(item: HomeItem) = when (item) {
            is HomeItem.Divider -> bind(item)
            is HomeItem.ArtistItem -> bind(item)
        }

        private fun bind(item: HomeItem.Divider) {
            name.text = when (item.title) {
                HomeTitle.RECENTLY_PLAYED -> "recently played"
                HomeTitle.FEATURED -> "featured"
                HomeTitle.LATEST_RECORDINGS -> "latest recordings"
                HomeTitle.ALL_ARTISTS -> "all artists"
            }
            name.setTextColor(ContextCompat.getColor(containerView.context, android.R.color.holo_red_dark))
        }

        private fun bind(item: HomeItem.ArtistItem) {
            name.text = item.artist.name
            name.setTextColor(ContextCompat.getColor(containerView.context, android.R.color.black))
        }

    }
}